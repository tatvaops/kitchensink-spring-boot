package com.mongodb.kitchensink.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import javax.validation.Valid;
import javax.validation.ConstraintViolationException;
import org.springframework.validation.annotation.Validated;

import com.mongodb.kitchensink.demo.models.Member;
import com.mongodb.kitchensink.demo.services.MemberService;

// import jakarta.validation.Valid;

@Controller
@RequestMapping
@Validated
public class MemberController {
    @Autowired
    private MemberService service;
    
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("members", service.findAll());
        model.addAttribute("member", new Member());
        return "index";
    }
    
    @GetMapping("/members")
    @ResponseBody
    public ResponseEntity<List<Member>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }
    
    @GetMapping("/members/{id}")
    @ResponseBody
    public ResponseEntity<Member> getById(@PathVariable String id) {
        return service.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/members/api")
    @ResponseBody
    public ResponseEntity<Member> create(@Valid @RequestBody Member entity, BindingResult result) {
        if (result.hasErrors()) {
            throw new ConstraintViolationException(null);
        }
        return ResponseEntity.ok(service.save(entity));
    }
    
    @PostMapping("/members")
    public String createMember(@Valid @ModelAttribute Member member, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("members", service.findAll());
            model.addAttribute("member", member);
            model.addAttribute("errors", result.getAllErrors());
            return "index";
        }
        
        try {
            service.save(member);
            return "redirect:/";
        } catch (Exception e) {
            model.addAttribute("members", service.findAll());
            model.addAttribute("member", member);
            model.addAttribute("error", "Failed to save member. Please try again.");
            return "index";
        }
    }
    
    @PutMapping("/members/{id}")
    @ResponseBody
    public ResponseEntity<Member> update(@PathVariable String id, @Valid @RequestBody Member entity, BindingResult result) {
        if (result.hasErrors()) {
            throw new ConstraintViolationException(null);
        }
        return service.findById(id)
            .map(existing -> {
                entity.setId(id);
                return ResponseEntity.ok(service.save(entity));
            })
            .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/members/{id}")
    @ResponseBody
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
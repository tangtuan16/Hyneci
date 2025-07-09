package com.example.LibraryApis.Controllers;

import com.example.LibraryApis.Models.Notification;
import com.example.LibraryApis.Services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    @Autowired
    private NotificationService service;

    @GetMapping
    public List<Notification> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notification> getById(@PathVariable int id) {
        Notification entity = service.getById(id);
        return entity != null ? ResponseEntity.ok(entity) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Notification create(@RequestBody Notification entity) {
        return service.save(entity);
    }

    @PutMapping("/{id}")
    public Notification update(@PathVariable int id, @RequestBody Notification entity) {
        entity.setId(id);
        return service.save(entity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        service.delete(id);
    }
}

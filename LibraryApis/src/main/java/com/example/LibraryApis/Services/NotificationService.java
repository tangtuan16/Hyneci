package com.example.LibraryApis.Services;

import com.example.LibraryApis.Models.Notification;
import com.example.LibraryApis.Repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository repo;

    public List<Notification> getAll() {
        return repo.findAll();
    }

    public Notification getById(int id) {
        return repo.findById(id).orElse(null);
    }

    public Notification save(Notification entity) {
        return repo.save(entity);
    }

    public void delete(int id) {
        repo.deleteById(id);
    }
}
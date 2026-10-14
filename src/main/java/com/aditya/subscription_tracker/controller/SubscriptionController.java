
package com.aditya.subscription_tracker.controller;

import com.aditya.subscription_tracker.service.SubscriptionService;
import org.springframework.web.bind.annotation.RestController;
import com.aditya.subscription_tracker.entity.Subscription;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Optional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

    private final SubscriptionService service;

    public SubscriptionController(SubscriptionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Subscription> create(@RequestBody Subscription subscription) {
        return new ResponseEntity<>(service.save(subscription), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Subscription>> getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subscription> getById(@PathVariable Long id) {
        Optional<Subscription> s = service.getById(id);

        return s.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Subscription> update(
            @PathVariable Long id,
            @RequestBody Subscription updated) {

        Optional<Subscription> existing = service.getById(id);

        if (existing.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Subscription s = existing.get();
        s.setName(updated.getName());
        s.setType(updated.getType());
        s.setAmount(updated.getAmount());
        s.setBillingCycle(updated.getBillingCycle());
        s.setNextDueDate(updated.getNextDueDate());
        s.setCategory(updated.getCategory());
        s.setActive(updated.isActive());

        return new ResponseEntity<>(service.save(s), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        if (service.getById(id).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        service.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<Subscription>> upcoming(
            @RequestParam(defaultValue = "7") int days) {

        return new ResponseEntity<>(service.getUpcoming(days), HttpStatus.OK);
    }

    @GetMapping("/monthly-total")
    public ResponseEntity<Map<String, Double>> monthlyTotal() {
        return new ResponseEntity<>(
                Map.of("monthlyTotal", service.getMonthlyTotal()),
                HttpStatus.OK
        );
    }

    @PutMapping("/{id}/pay")
    public ResponseEntity<Subscription> markPaid(@PathVariable Long id) {
        return service.markAsPaid(id)
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}

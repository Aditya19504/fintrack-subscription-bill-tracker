
package com.aditya.subscription_tracker.service;

import com.aditya.subscription_tracker.entity.Subscription;
import com.aditya.subscription_tracker.repository.SubscriptionRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

@Service
public class SubscriptionService {

    private final SubscriptionRepository repository;

    public SubscriptionService(SubscriptionRepository repository) {
        this.repository = repository;
    }

    public Subscription save(Subscription subscription) {
        return repository.save(subscription);
    }

    public List<Subscription> getAll() {
        return repository.findAll();
    }

    public Optional<Subscription> getById(Long id) {
        return repository.findById(id);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public List<Subscription> getUpcoming(int days) {
        LocalDate today = LocalDate.now();

        return repository.findByActiveTrueAndNextDueDateBetween(
                today,
                today.plusDays(days)
        );
    }

    public double getMonthlyTotal() {
        double total = 0;

        for (Subscription s : repository.findByActiveTrue()) {
            switch (s.getBillingCycle()) {
                case WEEKLY -> total += s.getAmount() * 52 / 12;
                case MONTHLY -> total += s.getAmount();
                case YEARLY -> total += s.getAmount() / 12;
            }
        }

        return Math.round(total * 100.0) / 100.0;
    }

    public Optional<Subscription> markAsPaid(Long id) {
        Optional<Subscription> optional = repository.findById(id);

        if (optional.isEmpty()) {
            return optional;
        }

        Subscription s = optional.get();
        LocalDate due = s.getNextDueDate();

        switch (s.getBillingCycle()) {
            case WEEKLY -> s.setNextDueDate(due.plusWeeks(1));
            case MONTHLY -> s.setNextDueDate(due.plusMonths(1));
            case YEARLY -> s.setNextDueDate(due.plusYears(1));
        }

        return Optional.of(repository.save(s));
    }
}

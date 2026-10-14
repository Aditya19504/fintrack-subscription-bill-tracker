
package com.aditya.subscription_tracker.repository;

import com.aditya.subscription_tracker.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    List<Subscription> findByActiveTrue();

    List<Subscription> findByActiveTrueAndNextDueDateBetween(
            LocalDate from, LocalDate to);
}

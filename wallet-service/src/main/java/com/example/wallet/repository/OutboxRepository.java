package com.example.wallet.repository;

import com.example.wallet.model.OutboxEvent;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OutboxRepository {
    private final JdbcTemplate jdbcTemplate;

    public OutboxRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(OutboxEvent event) {
        String sql = "INSERT INTO outbox (aggregate_id, event_type, event_data, status) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, event.getAggregateId(), event.getEventType(), event.getEventData(), event.getStatus());
    }

    public List<OutboxEvent> findPendingEvents() {
        String sql = "SELECT * FROM outbox WHERE status = 'PENDING'";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(OutboxEvent.class));
    }

    public void updateStatus(Long id, String status) {
        String sql = "UPDATE outbox SET status = ? WHERE id = ?";
        jdbcTemplate.update(sql, status, id);
    }
}

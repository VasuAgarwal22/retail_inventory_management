package org.example.retail_inventory_managment.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.retail_inventory_managment.enums.ModerationAction;

import java.time.Instant;

@Entity
@Table(name = "review_moderation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewModeration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "review_id", nullable = false)
    private Review review;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "moderator_id", nullable = false)
    private User moderator;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private ModerationAction action;

    @Column(columnDefinition = "text")
    private String comments;

    @Column(nullable = false)
    private Instant moderatedDate;
}

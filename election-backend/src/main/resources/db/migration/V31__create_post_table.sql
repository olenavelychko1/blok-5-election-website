CREATE TABLE post
(
    id         INT AUTO_INCREMENT PRIMARY KEY UNIQUE,
    user_id    INT          NOT NULL,
    title      VARCHAR(100) NOT NULL,
    content    TEXT         NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

INSERT INTO post (user_id, title, content, created_at)
VALUES (1, '1. How are new political parties registered for the national elections?',
        'What requirements must a new party meet to appear on the ballot for the Tweede Kamer?',
        '2024-09-05 10:15:00'),

       (1, '2. How do postal and early voting options work in the Netherlands?',
        'I know some countries allow extensive early voting—what are the rules here?',
        '2024-10-22 16:10:00'),

       (1, '3. What happens if no coalition can be formed?',
        'Is there a legal procedure for situations where parties can’t reach an agreement?',
        '2024-12-07 18:30:00'),

       (1, '4. How do campaign debates influence voter decisions?',
        'Are televised debates known to significantly shift public opinion during Tweede Kamer elections?',
        '2025-01-18 13:45:00'),

       (1, '5. What does voter turnout typically look like in the Netherlands?',
        'I’m curious how Dutch turnout compares to other European countries during national elections.',
        '2025-02-01 09:20:00'),

       (1, '6. How do smaller parties influence policy in the Dutch system?',
        'Even small parties win seats in the Tweede Kamer. How much impact can they realistically have on legislation?',
        '2025-03-14 17:55:00'),

       (1, '7. What is the role of a party leader during election campaigns?',
        'Do party leaders have a formal role in the parliamentary process, or is their influence mostly political?',
        '2025-04-28 10:40:00'),

       (1, '8. How does coalition building work after the elections?',
        'I often hear about long negotiations. How do parties decide who forms a coalition, and what factors matter most?',
        '2025-05-10 11:05:00'),

       (1, '9. What are the main responsibilities of the Tweede Kamer?',
        'I know it’s the lower house of Parliament, but I’m still unclear about its exact powers, especially compared to the Eerste Kamer.',
        '2025-06-21 15:30:00'),

       (1, '10. How reliable are exit polls during Dutch elections?',
        'Can exit polls be trusted to reflect the final results, or are they often inaccurate?',
        '2025-07-12 12:00:00'),

       (1, '11. Tips for evaluating party programmes',
        'What should I focus on when reading party programmes to understand which plans are realistic and achievable?',
        '2025-08-30 19:45:00'),

       (1, '12. How does proportional representation work in the Netherlands?',
        'I’m trying to understand how seat allocation works in the Tweede Kamer and how it differs from majority systems in other countries...',
        '2025-09-18 08:00:00'),

       (1, '13. Where can I find my polling station for the parliamentary elections?',
        'Does anyone know where the polling locations are in Amsterdam-Zuidoost?',
        '2025-10-02 14:10:00'),

       (1, '14. How do Dutch parties differ in their climate policies?',
        'I’m comparing the party programmes for the upcoming parliamentary elections, but I’m still unsure which parties propose stricter climate targets...',
        '2025-11-10 09:24:00'),

       (1, '15. How do Dutch parties differ in their climate policies?',
        'I’m comparing the party programmes for the upcoming parliamentary elections, but I’m still unsure which parties propose stricter climate targets...',
        '2025-11-11 09:24:00');

-- Create template table to store background templates associated with themes
CREATE TABLE template (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    image_path VARCHAR(255) NOT NULL,
    theme_id UUID REFERENCES theme(id) ON DELETE CASCADE,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Seed templates for existing themes
INSERT INTO template (id, name, image_path, theme_id, created_at) VALUES
-- Atendimento de Cura
(gen_random_uuid(), 'Atendimento de Cura Template 1', 'atendimento_de_cura_2.png', 'ce489ba3-2f01-4dfa-b094-70313c3f5a92', CURRENT_TIMESTAMP),

-- Gira de Erês
(gen_random_uuid(), 'Gira de Erês Template 1', 'gira_de_eres.png', '6e8437d3-60b8-4966-b0f7-b28b84e78913', CURRENT_TIMESTAMP),

-- Feijoada dos Vovôs (2 templates)
(gen_random_uuid(), 'Feijoada dos Vovôs Template 1', 'feijoada_dos_vovos.png', '01f3565b-d625-490d-bfcd-f5284f26e0a0', CURRENT_TIMESTAMP),
(gen_random_uuid(), 'Feijoada dos Vovôs Template 2', 'feijoada_dos_vovos_2.png', '01f3565b-d625-490d-bfcd-f5284f26e0a0', CURRENT_TIMESTAMP),

-- Festa de 7 Saias
(gen_random_uuid(), 'Festa de 7 Saias Template 1', 'festa_de_7_saias.png', '92f3b2ae-4e92-49df-8e5f-70b1c860cc4a', CURRENT_TIMESTAMP),

-- Festa de Erês e Pretos Velhos
(gen_random_uuid(), 'Festa de Erês e Pretos Velhos Template 1', 'festa_de_eres_e_pretos_velhos.png', 'c29b995e-b025-4fa1-be61-54ac2b28984a', CURRENT_TIMESTAMP),

-- Gira de Cura Caboclos e Boiadeiros
(gen_random_uuid(), 'Gira de Cura Caboclos e Boiadeiros Template 1', 'gira_de_cura_caboclos_e_boiadeiros.png', 'fde1d1ab-34fe-4a7c-b700-462b44b7e6e6', CURRENT_TIMESTAMP),

-- Gira de Encerramento (Exu)
(gen_random_uuid(), 'Gira de Encerramento (Exu) Template 1', 'gira_de_encerramento_exu.png', '9e8736b6-51a7-4ac8-b027-75e467e95124', CURRENT_TIMESTAMP),

-- Gira de Erês e Cura
(gen_random_uuid(), 'Gira de Erês e Cura Template 1', 'gira_de_eres_e_cura.png', '1f521844-771c-4d68-80f9-5a39c8c274cb', CURRENT_TIMESTAMP),

-- Gira de Exu e Cura (3 templates)
(gen_random_uuid(), 'Gira de Exu e Cura Template 1', 'gira_de_exu_e_cura.png', '189951bc-a581-4788-a635-2642add90358', CURRENT_TIMESTAMP),
(gen_random_uuid(), 'Gira de Exu e Cura Template 2', 'gira_de_exu_e_cura_2.png', '189951bc-a581-4788-a635-2642add90358', CURRENT_TIMESTAMP),
(gen_random_uuid(), 'Gira de Exu e Cura Template 3', 'gira_de_exu_e_cura_3.png', '189951bc-a581-4788-a635-2642add90358', CURRENT_TIMESTAMP),

-- Gira de Pretos Velhos e Cura
(gen_random_uuid(), 'Gira de Pretos Velhos e Cura Template 1', 'gira_de_pretos_velhos_e_cura_2.png', '3410a41b-f54f-49e8-88ab-bfeb40f8f838', CURRENT_TIMESTAMP);

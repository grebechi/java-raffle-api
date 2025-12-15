CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Tabela principal
CREATE TABLE participants (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    registration_number BIGINT NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    keyword VARCHAR(100) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    global_counter BIGINT NOT NULL
);

CREATE INDEX idx_participants_keyword
    ON participants (keyword);

-- Sequência auxiliar por keyword (case-insensitive)
CREATE TABLE keyword_counters (
    keyword_lower VARCHAR(100) PRIMARY KEY,
    counter BIGINT NOT NULL
);

-- Sequência global para contagem total
CREATE SEQUENCE global_participant_counter START 1;

-- Função para incrementar contador por keyword (case-insensitive) e contador global
CREATE OR REPLACE FUNCTION increment_keyword_counter()
RETURNS TRIGGER AS $$
DECLARE
    keyword_lower_text VARCHAR(100);
BEGIN
    -- normaliza keyword para minúsculas
    keyword_lower_text := LOWER(NEW.keyword);

    LOOP
        -- tenta atualizar contador por keyword
        UPDATE keyword_counters
        SET counter = counter + 1
        WHERE keyword_lower = keyword_lower_text;
        IF FOUND THEN
            NEW.registration_number = (SELECT counter FROM keyword_counters WHERE keyword_lower = keyword_lower_text);
            EXIT;
        END IF;

        -- se não existir, insere com valor 1
        BEGIN
            INSERT INTO keyword_counters(keyword_lower, counter) VALUES (keyword_lower_text, 1);
            NEW.registration_number = 1;
            EXIT;
        EXCEPTION WHEN unique_violation THEN
            -- alguém inseriu ao mesmo tempo, tenta novamente
        END;
    END LOOP;

    -- atualiza contador global usando sequência
    NEW.global_counter := nextval('global_participant_counter');

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Trigger para chamar a função antes do insert
CREATE TRIGGER trg_increment_keyword_counter
BEFORE INSERT ON participants
FOR EACH ROW
EXECUTE FUNCTION increment_keyword_counter();

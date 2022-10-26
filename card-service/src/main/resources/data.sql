INSERT INTO DECK (id, name, created_on, updated_on) VALUES (NEXTVAL('deck_id_seq'), 'German Vocabulary', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO CARD (id, deck_id, front, back) VALUES (NEXTVAL('card_id_seq'), CURRVAL('deck_id_seq'), 'das Haus', 'the house');
INSERT INTO CARD (id, deck_id, front, back) VALUES (NEXTVAL('card_id_seq'), CURRVAL('deck_id_seq'), 'der Hund', 'the dog');
INSERT INTO CARD (id, deck_id, front, back) VALUES (NEXTVAL('card_id_seq'), CURRVAL('deck_id_seq'), 'die Katze', 'the cat');
INSERT INTO CARD (id, deck_id, front, back) VALUES (NEXTVAL('card_id_seq'), CURRVAL('deck_id_seq'), 'das Limon', 'the lemon');

INSERT INTO DECK (id, name, created_on, updated_on) VALUES (NEXTVAL('deck_id_seq'), 'Spanish Vocabulary', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);


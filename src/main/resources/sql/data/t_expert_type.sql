CREATE TABLE t_expert_type
(
    id INT(4) NOT NULL AUTO_INCREMENT,
    expert_id INT(4) NOT NULL,
    prize_id INT(4) NOT NULL,
    CONSTRAINT pk_t_expert_type_id PRIMARY KEY (id),
    CONSTRAINT fk_t_expert_type_expert_id FOREIGN KEY (expert_id) REFERENCES t_expert(id),
    CONSTRAINT fk_t_expert_type_prize_id FOREIGN KEY (prize_id) REFERENCES t_prize(id)
)

INSERT INTO t_expert_type(expert_id, prize_id) VALUES(1,25);
INSERT INTO t_expert_type(expert_id, prize_id) VALUES(1,45);
INSERT INTO t_expert_type(expert_id, prize_id) VALUES(1,48);

INSERT INTO t_expert_type(expert_id, prize_id) VALUES(2,1);
INSERT INTO t_expert_type(expert_id, prize_id) VALUES(2,3);
INSERT INTO t_expert_type(expert_id, prize_id) VALUES(2,9);
INSERT INTO t_expert_type(expert_id, prize_id) VALUES(2,11);

SELECT * FROM t_expert_type;
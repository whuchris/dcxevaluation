CREATE TABLE t_prize
(
    id INT(4) NOT NULL AUTO_INCREMENT,
    f_type INT(1) DEFAULT 0,
    s_type INT(1) DEFAULT 0,
    t_type INT(1) DEFAULT 0,
    CONSTRAINT pk_t_prize_id PRIMARY KEY (id)
)

INSERT INTO t_prize(f_type, s_type, t_type) VALUES(1,1,1);
INSERT INTO t_prize(f_type, s_type, t_type) VALUES(1,1,2);
INSERT INTO t_prize(f_type, s_type, t_type) VALUES(1,2,1);
INSERT INTO t_prize(f_type, s_type, t_type) VALUES(1,2,2);

INSERT INTO t_prize(f_type, s_type, t_type) VALUES(2,1,1);
INSERT INTO t_prize(f_type, s_type, t_type) VALUES(2,1,2);
INSERT INTO t_prize(f_type, s_type, t_type) VALUES(2,2,1);
INSERT INTO t_prize(f_type, s_type, t_type) VALUES(2,2,2);
INSERT INTO t_prize(f_type, s_type, t_type) VALUES(2,3,1);
INSERT INTO t_prize(f_type, s_type, t_type) VALUES(2,3,2);
INSERT INTO t_prize(f_type, s_type, t_type) VALUES(2,4,1);
INSERT INTO t_prize(f_type, s_type, t_type) VALUES(2,4,2);

INSERT INTO t_prize(f_type, s_type, t_type) VALUES(3,1,1);
INSERT INTO t_prize(f_type, s_type, t_type) VALUES(3,1,2);
INSERT INTO t_prize(f_type, s_type, t_type) VALUES(3,2,1);
INSERT INTO t_prize(f_type, s_type, t_type) VALUES(3,2,2);
INSERT INTO t_prize(f_type, s_type, t_type) VALUES(3,3,1);
INSERT INTO t_prize(f_type, s_type, t_type) VALUES(3,3,2);
INSERT INTO t_prize(f_type, s_type, t_type) VALUES(3,4,1);
INSERT INTO t_prize(f_type, s_type, t_type) VALUES(3,4,2);

INSERT INTO t_prize(f_type, s_type, t_type) VALUES(4,1,1);
INSERT INTO t_prize(f_type, s_type, t_type) VALUES(4,1,2);
INSERT INTO t_prize(f_type, s_type, t_type) VALUES(4,2,1);
INSERT INTO t_prize(f_type, s_type, t_type) VALUES(4,2,2);
INSERT INTO t_prize(f_type, s_type, t_type) VALUES(4,3,1);
INSERT INTO t_prize(f_type, s_type, t_type) VALUES(4,3,2);
INSERT INTO t_prize(f_type, s_type, t_type) VALUES(4,4,1);
INSERT INTO t_prize(f_type, s_type, t_type) VALUES(4,4,2);

INSERT INTO t_prize(f_type, s_type, t_type) VALUES(5,1,1);
INSERT INTO t_prize(f_type, s_type, t_type) VALUES(5,1,2);
INSERT INTO t_prize(f_type, s_type, t_type) VALUES(5,3,1);
INSERT INTO t_prize(f_type, s_type, t_type) VALUES(5,3,2);
INSERT INTO t_prize(f_type, s_type, t_type) VALUES(5,4,1);
INSERT INTO t_prize(f_type, s_type, t_type) VALUES(5,4,2);

INSERT INTO t_prize(f_type, s_type, t_type) VALUES(6,1,1);
INSERT INTO t_prize(f_type, s_type, t_type) VALUES(6,1,2);
INSERT INTO t_prize(f_type, s_type, t_type) VALUES(6,3,1);
INSERT INTO t_prize(f_type, s_type, t_type) VALUES(6,3,2);
INSERT INTO t_prize(f_type, s_type, t_type) VALUES(6,4,1);
INSERT INTO t_prize(f_type, s_type, t_type) VALUES(6,4,2);

INSERT INTO t_prize(f_type, s_type, t_type) VALUES(7,1,1);
INSERT INTO t_prize(f_type, s_type, t_type) VALUES(7,1,2);
INSERT INTO t_prize(f_type, s_type, t_type) VALUES(7,2,1);
INSERT INTO t_prize(f_type, s_type, t_type) VALUES(7,2,2);
INSERT INTO t_prize(f_type, s_type, t_type) VALUES(7,3,1);
INSERT INTO t_prize(f_type, s_type, t_type) VALUES(7,3,2);
INSERT INTO t_prize(f_type, s_type, t_type) VALUES(7,4,1);
INSERT INTO t_prize(f_type, s_type, t_type) VALUES(7,4,2);

SELECT * FROM t_prize;

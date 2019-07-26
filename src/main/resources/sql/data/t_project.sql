CREATE TABLE t_project
(
    id INT(4) NOT NULL AUTO_INCREMENT,
    prize_id INT(4) NOT NULL,
    expert_id INT(4),
    assessment_state INT(4) NOT NULL,
    prize_class INT(4) NOT NULL,
    application_time DATE,
    name VARCHAR(50),
    f_grade INT(4),
    l_grade INT(4),
    CONSTRAINT pk_t_project_id PRIMARY KEY (id),
    CONSTRAINT fk_t_project_prize_id FOREIGN KEY (prize_id) REFERENCES t_prize(id),
    CONSTRAINT fk_t_project_expert_id FOREIGN KEY (expert_id) REFERENCES t_expert(id)
);
ALTER TABLE t_project ADD f_grade INT(4);

INSERT INTO t_project(prize_id, assessment_state,prize_class,expert_id,name) values(1, 1, 5,2,'建筑旅游');
INSERT INTO t_project(prize_id, assessment_state,prize_class,expert_id,name) values(3, 1, 5,2,'建筑旅游');
INSERT INTO t_project(prize_id, assessment_state,prize_class,expert_id,name) values(9, 1, 5,2,'建筑旅游');
INSERT INTO t_project(prize_id, assessment_state,prize_class,expert_id,name) values(11, 1, 5,2,'建筑旅游');

INSERT INTO t_project(prize_id, assessment_state,prize_class,expert_id,name) values(25, 1, 5,1,'城市建设');
INSERT INTO t_project(prize_id, assessment_state,prize_class,expert_id,name) values(45, 1, 5,1,'城市建设');
INSERT INTO t_project(prize_id, assessment_state,prize_class,expert_id,name) values(48, 1, 5,1,'城市建设');

INSERT INTO t_project(prize_id, assessment_state,prize_class,name) values(1, 1, 5,'城市建设');
INSERT INTO t_project(prize_id, assessment_state,prize_class,name) values(3, 1, 5,'城市建设');
INSERT INTO t_project(prize_id, assessment_state,prize_class,name) values(9, 1, 5,'城市建设');

INSERT INTO t_project(prize_id, assessment_state,prize_class,name) values(35, 0, 5,'test4');
INSERT INTO t_project(prize_id, assessment_state,prize_class,name) values(36, 0, 5,'test5');
INSERT INTO t_project(prize_id, assessment_state,prize_class,name) values(37, 20, 5,'test6');
INSERT INTO t_project(prize_id, assessment_state,prize_class,name) values(37, 20, 5,'test7');

DELETE FROM t_project where id = 8
SELECT * FROM t_project;

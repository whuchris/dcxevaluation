CREATE TABLE t_project_assignment
(
	id INT(4) NOT NULL auto_increment,
    project_id INT(4) NOT NULL,
    expert_id INT(4) NOT NULL,
    state INT(2) NOT NULL,
    CONSTRAINT pk_t_project_assignment_id PRIMARY KEY(id),
    CONSTRAINT fk_t_project_assignment_expert_id FOREIGN KEY(expert_id) REFERENCES t_expert(id),
	CONSTRAINT fk_t_project_assignment_project_id FOREIGN KEY(project_id) REFERENCES t_project(id),
    CONSTRAINT ck_t_project_assignment_state CHECK(state IN(1,2))
);

INSERT INTO t_project_assignment(project_id, expert_id, state) VALUES(1,1,1);
INSERT INTO t_project_assignment(project_id, expert_id, state) VALUES(2,1,1);
INSERT INTO t_project_assignment(project_id, expert_id, state) VALUES(3,1,1);

INSERT INTO t_project_assignment(project_id, expert_id, state) VALUES(4,2,1);
INSERT INTO t_project_assignment(project_id, expert_id, state) VALUES(5,2,1);
INSERT INTO t_project_assignment(project_id, expert_id, state) VALUES(6,2,1);
INSERT INTO t_project_assignment(project_id, expert_id, state) VALUES(7,2,1);

INSERT INTO t_project_assignment(project_id, expert_id, state) VALUES(1,1,2);

SELECT * FROM t_project_assignment;

DELETE FROM t_project_assignment WHERE id = 8;

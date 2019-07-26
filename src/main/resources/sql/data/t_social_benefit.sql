CREATE TABLE t_social_benefit
(
    id INT(4) NOT NULL AUTO_INCREMENT,
    project_id INT(4) NOT NULL,
    expert_id INT(4) NOT NULL,
    effect FLOAT(5, 2) NOT NULL,
    state INT(1) NOT NULL,
    CONSTRAINT pk_t_social_benefit_id PRIMARY KEY (id),
    CONSTRAINT fk_t_social_benefit_project_id FOREIGN KEY (project_id) REFERENCES t_project(id),
    CONSTRAINT fk_t_social_benefit_expert_id FOREIGN KEY (expert_id) REFERENCES t_expert(id),
    CONSTRAINT ck_t_social_benefit_4_state CHECK (state IN(1,2))
)

SELECT * FROM t_social_benefit;
DELETE FROM t_social_benefit WHERE id = 4;
DELETE FROM t_social_benefit WHERE id = 10;

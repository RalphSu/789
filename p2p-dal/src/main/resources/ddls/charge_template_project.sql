use yr789; 
DROP TABLE IF EXISTS charge_template_project; 
CREATE TABLE charge_template_project (
	charge_template_project_id BIGINT,
	charge_project_id BIGINT,
	charge_template_id BIGINT
);
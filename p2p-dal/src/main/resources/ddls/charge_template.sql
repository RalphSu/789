use yr789; 
DROP TABLE IF EXISTS charge_template; 
CREATE TABLE charge_template (
	charge_template_id BIGINT,
	template_name VARCHAR(400),
	role_id INT,
	template_type VARCHAR(400),
	rule_model VARCHAR(400),
	charge_method VARCHAR(400),
	charge_date DATETIME,
	create_date DATETIME,
	modify_date DATETIME
);
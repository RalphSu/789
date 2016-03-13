use yr789; 
DROP TABLE IF EXISTS division_rule; 
CREATE TABLE division_rule (
	rule_id BIGINT,
	role_id INT,
	rule_template_id BIGINT,
	division_rule DECIMAL
);
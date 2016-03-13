use yr789; 
DROP TABLE IF EXISTS division_template; 
CREATE TABLE division_template (
	division_template_id BIGINT,
	template_name VARCHAR(400),
	division_phase VARCHAR(400),
	division_way VARCHAR(400),
	template_status VARCHAR(400),
	create_date DATETIME,
	modify_date DATETIME,
	template_note VARCHAR(400)
);
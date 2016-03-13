use yr789; 
DROP TABLE IF EXISTS bank_info; 
CREATE TABLE bank_info (
	bank_id bigint,
	bank_name VARCHAR(400),
	bank_code VARCHAR(400),
	bank_logoUrl VARCHAR(400),
	deduct VARCHAR(400),
	sigle_deduct_limit int,
	odd_deduct_limit int,
	syn_channel VARCHAR(400),
	withdrawal VARCHAR(400),
	single_withdrawal_limit int,
	odd_withdrawal_limit int,
	public_accounts VARCHAR(400)
);
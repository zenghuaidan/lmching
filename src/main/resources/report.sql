SELECT id, 
email, 
name, 
type as category,
entrant_name,
phone,
postal_code,
address,
city,
country,
paid,
paid_confirm,
create_time,
active,
(SELECT sum(entry_fee) FROM db_hkda.entry where user_id = user.id) as `own/paid`,
(case  
    when type = 'OVERSEAS' then 'USD'
    when type = 'MAINLAND_CHINA' then 'RMB'    
else 'HKD'
end) as `HKD/USD/RMB`,
(case  
    when use_paypal then 'Paid by Paypal'
    when payment_slip is not null and payment_slip <> '' then 'Paid by Payment Slip'    
    when cheque_account_name is not null and cheque_account_name <> '' then 'Paid by Cheque'    
else 'No Paid'
end) as `Paid Method` 
FROM db_hkda.user order by id asc;

SELECT id,
user_id,
(SELECT (case 
	when use_paypal then 'Paid by Paypal'
    when payment_slip is not null and payment_slip <> '' then 'Paid by Payment Slip'    
    when cheque_account_name is not null and cheque_account_name <> '' then 'Paid by Cheque' else 'No Paid' end) FROM db_hkda.user where id=user_id) as 'Paid Method',
(SELECT email FROM db_hkda.user where id=user_id) as 'user email',
(SELECT type FROM db_hkda.user where id=user_id) as 'user category',
(SELECT nameen FROM db_hkda.category where id=main_design_category) as 'main category',
(SELECT nameen FROM db_hkda.category where id=sub_design_category) as 'sub category', 
title,
pdf_file_name,
link_to_website,
link_to_video,
user_name_and_password_for_link,
single,
label_count,
key_page,
entry_fee,
credit,
create_time FROM db_hkda.entry order by user_id asc;


SELECT 
user_id,
(SELECT email FROM db_hkda.user where id=user_id) as 'user email',
(SELECT name FROM db_hkda.user where id=user_id) as 'user name',
(SELECT type FROM db_hkda.user where id=user_id) as 'user category',
(SELECT entrant_name FROM db_hkda.user where id=user_id) as 'user entrant name',
(SELECT phone FROM db_hkda.user where id=user_id) as 'user phone',
(SELECT postal_code FROM db_hkda.user where id=user_id) as 'user postal code',
(SELECT address FROM db_hkda.user where id=user_id) as 'user address',
(SELECT city FROM db_hkda.user where id=user_id) as 'user city',
(SELECT country FROM db_hkda.user where id=user_id) as 'user country',
(SELECT paid FROM db_hkda.user where id=user_id) as 'user paid',
(SELECT paid_confirm FROM db_hkda.user where id=user_id) as 'paid confirm',
(SELECT create_time FROM db_hkda.user where id=user_id) as 'user create time',
(SELECT active FROM db_hkda.user where id=user_id) as 'active',
(SELECT sum(entry_fee) FROM db_hkda.entry e where e.user_id = ee.user_id) as `own/paid`,
(case  
    when type = 'OVERSEAS' then 'USD'
    when type = 'MAINLAND_CHINA' then 'RMB'    
else 'HKD'
end) as `HKD/USD/RMB`,
(SELECT (case 
	when use_paypal then 'Paid by Paypal'
    when payment_slip is not null and payment_slip <> '' then 'Paid by Payment Slip'    
    when cheque_account_name is not null and cheque_account_name <> '' then 'Paid by Cheque' else 'No Paid' end) FROM db_hkda.user where id=user_id) as 'Paid Method',
id as 'entry id',
title,
(SELECT nameen FROM db_hkda.category where id=main_design_category) as 'main category',
(SELECT nameen FROM db_hkda.category where id=sub_design_category) as 'sub category', 
pdf_file_name,
link_to_website,
link_to_video,
user_name_and_password_for_link,
single,
label_count,
key_page,
entry_fee,
credit,
create_time as 'entry create time'
FROM db_hkda.entry ee order by user_id asc;






SELECT 
u.id,
email as 'user email',
name as 'user name',
u.type as 'user category',
entrant_name  as 'user entrant name',
phone  as 'user phone',
postal_code  as 'user postal code',
address  as 'user address',
city  as 'user city',
country  as 'user country',
paid  as 'user paid',
paid_confirm as 'paid confirm',
u.create_time  as 'user create time',
active  as 'active',
(SELECT sum(entry_fee) FROM db_hkda.entry e where e.user_id = u.id) as `own/paid`,
(case  
    when u.type = 'OVERSEAS' then 'USD'
    when u.type = 'MAINLAND_CHINA' then 'RMB'    
else 'HKD'
end) as `HKD/USD/RMB`,
(case 
	when use_paypal then 'Paid by Paypal'
    when payment_slip is not null and payment_slip <> '' then 'Paid by Payment Slip'    
    when cheque_account_name is not null and cheque_account_name <> '' then 'Paid by Cheque' else 'No Paid' end) as 'Paid Method',
    
ee.id as 'entry id',
CONCAT(lpad(u.id, 4, 0), '-', replace(left((SELECT nameen FROM db_hkda.category where id=sub_design_category), 4),'.',''), '-', lpad(ee.id, 4, 0), (case when single then '-S1' else concat('-M', label_count) end), '-1') AS 'entry no',
title,
(SELECT nameen FROM db_hkda.category where id=main_design_category) as 'main category',
(SELECT nameen FROM db_hkda.category where id=sub_design_category) as 'sub category', 
pdf_file_name,
link_to_website,
link_to_video,
user_name_and_password_for_link,
single,
label_count,

entry_fee,
credit,
ee.create_time as 'entry create time'
FROM db_hkda.user u left join db_hkda.entry ee on u.id=ee.user_id order by u.id asc;

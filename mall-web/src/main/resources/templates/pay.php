<?php //php 7.0.8

    
        
$secret = '6fe97e80-6caa-4a14-91ee-31e669df5579'; // 签名密钥
$fields = array(
'merchant_reference' => '4',
'currency' => 'HKD',
'amount' => '1.00',
'customer_ip' => '123.123.123.123',
'customer_first_name' => 'John',
'customer_last_name' => 'Doe',
'customer_address' => 'address',
'customer_phone' => '0123123123',
'customer_email' => 'someone@gmail.com',
'customer_state' => 'NY',
'customer_country' => 'US',
'return_url' => 'https://demo.shop.com/payment/return',
'network' => 'Wechat',
'subject' => 'IphoneX'
);
ksort($fields);
$aa = hash('SHA512', http_build_query($fields) . $secret);
echo $aa
    
?>
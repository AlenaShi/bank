### Use for
-  create bank: create_bank {name} {commission_individual} {commission_legal},
- delete bank: delete_bank {id},
- get bank: get_bank {id},
- update bank: update_bank {name} {commission_individual} {commission_legal},
- create client: create_client {client_name} {individual} {bank},
- delete client: delete_client {id},
- get client: get_client {id},
- update client: update_client {id} {individual},
- create account: create_account {client_name} {bank_name},
- delete account: delete_account {id},
- get account: get_account {id},
- fill account: update_account {id} {balance},
- transfer money: transfer_money {accountNumberFrom} {accountNumberTo} {amount},
- for closing use: close.

Create correct database and run data.sql
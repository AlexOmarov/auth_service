INSERT INTO auth_service.privilege(name) values ('READ'), ('WRITE');
INSERT INTO auth_service.role(name) values ('ADMIN'), ('USER');
INSERT INTO auth_service.account(email,
                                 password,
                                 account_non_expired,
                                 account_non_locked,
                                 credentials_non_expired,
                                 enabled) values ('shtil.a@yandex.ru',
                                                  '$2y$12$7Ymzl97713skuQ1Nz53nieudY1enZLMMw963hBTJvayJDAfrrkLee',
                                                  true,
                                                  true,
                                                  true,
                                                  true),
                                                 ('shtil1.a@yandex.ru',
                                                  '$2y$12$7Ymzl97713skuQ1Nz53nieudY1enZLMMw963hBTJvayJDAfrrkLee',
                                                  true,
                                                  true,
                                                  true,
                                                  true);
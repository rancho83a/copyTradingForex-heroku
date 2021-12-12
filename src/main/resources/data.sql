-- user roles
INSERT INTO roles (id, role)
VALUES (1, 'MASTER');
INSERT INTO roles (id, role)
VALUES (2, 'TRADER');
INSERT INTO roles (id, role)
VALUES (3, 'INVESTOR');

-- some test users
INSERT INTO users (id, username, password, image_url, email, full_name, age, experience, buffered_amount)
VALUES (1, 'master', 'fae1489b450b4ed36cc5b6258b32e9c567a34e32f18e2c74e15834a344393b14cdf752347ddd0399',
        'https://res.cloudinary.com/drapmo8cx/image/upload/v1638274465/static/Aleksandar_Peychev_lcgmnh.jpg',
        'master@copytradingforex.com', 'Master Masterov', 33, 3, 0);

INSERT INTO users (id, username, password, email, full_name, age, experience, current_capital, total_deposit,
                   total_withdraw, image_url, buffered_amount)
VALUES (2, 'trader1', 'fae1489b450b4ed36cc5b6258b32e9c567a34e32f18e2c74e15834a344393b14cdf752347ddd0399',
        'trader1@copytradingforex.com', 'Trader1 Traderov1', 22, 2, 43923, 31242, 0,
        'https://res.cloudinary.com/drapmo8cx/image/upload/v1638274500/static/experience1_epfhyi.svg', 0);

INSERT INTO users (id, username, password, email, full_name, age, experience, current_capital, total_deposit,
                   total_withdraw, image_url, buffered_amount)
VALUES (3, 'trader2', 'fae1489b450b4ed36cc5b6258b32e9c567a34e32f18e2c74e15834a344393b14cdf752347ddd0399',
        'trader2@copytradingforex.com', 'Trader2 Traderov2', 44, 4, 6600, 6000, 0,
        'https://res.cloudinary.com/drapmo8cx/image/upload/v1638274500/static/experience1_epfhyi.svg', 0);

INSERT INTO users (id, username, password, email, full_name, age, experience, current_capital, total_deposit, image_url,
                   buffered_amount)
VALUES (4, 'investor1', 'fae1489b450b4ed36cc5b6258b32e9c567a34e32f18e2c74e15834a344393b14cdf752347ddd0399',
        'investor1@copytradingforex.com', 'Investor1 Investorov1', 55, 2, 10000, 10000,
        'https://res.cloudinary.com/drapmo8cx/image/upload/v1638274500/static/experience1_epfhyi.svg', 0);

INSERT INTO users (id, username, password, email, full_name, age, experience, current_capital, total_deposit,
                   total_withdraw, image_url, buffered_amount)
VALUES (5, 'investor2', 'fae1489b450b4ed36cc5b6258b32e9c567a34e32f18e2c74e15834a344393b14cdf752347ddd0399',
        'investor2@copytradingforex.com', 'Investor2 Investorov2', 40, 0, 1000, 1000, 0,
        'https://res.cloudinary.com/drapmo8cx/image/upload/v1638274500/static/experience1_epfhyi.svg', 0);
INSERT INTO users (id, username, password, email, full_name, age, experience, current_capital, total_deposit,
                   total_withdraw, image_url, buffered_amount)
VALUES (6, 'trader3', 'fae1489b450b4ed36cc5b6258b32e9c567a34e32f18e2c74e15834a344393b14cdf752347ddd0399',
        'trader3@copytradingforex.com', 'Trader3 Traderov3', 44, 4, 1600, 6000, 0,
        'https://res.cloudinary.com/drapmo8cx/image/upload/v1638274500/static/experience1_epfhyi.svg', 0);

-- -- user roles
-- -- master
INSERT INTO users_roles (`user_entity_id`, `roles_id`)
VALUES (1, 1);
INSERT INTO users_roles (`user_entity_id`, `roles_id`)
VALUES (1, 2);
INSERT INTO users_roles (`user_entity_id`, `roles_id`)
VALUES (1, 3);

-- -- trader1
INSERT INTO users_roles (`user_entity_id`, `roles_id`)
VALUES (2, 2);
-- -- trader2
INSERT INTO users_roles (`user_entity_id`, `roles_id`)
VALUES (3, 2);
-- investor1
INSERT INTO users_roles (`user_entity_id`, `roles_id`)
VALUES (4, 3);
-- investor2
INSERT INTO users_roles (`user_entity_id`, `roles_id`)
VALUES (5, 3);
-- trader3
INSERT INTO users_roles (`user_entity_id`, `roles_id`)
VALUES (6, 2);


-- countries
INSERT INTO countries (id, name, central_bank, flag_url)
VALUES ('1', 'USA', 'FED',
        'https://www.countryflags.com/wp-content/uploads/united-states-of-america-flag-png-large.png');;
INSERT INTO countries (id, name, central_bank, flag_url)
VALUES ('2', 'JAPAN', 'BOJ', 'https://cdn.countryflags.com/thumbs/japan/flag-800.png');
INSERT INTO countries (id, name, central_bank, flag_url)
VALUES ('3', 'UK', 'BOE',
        'https://cdn.countryflags.com/thumbs/united-kingdom/flag-800.png');
INSERT INTO countries (id, name, central_bank, flag_url)
VALUES ('4', 'EU', 'ECB', 'https://cdn.countryflags.com/thumbs/europe/flag-800.png');
INSERT INTO countries (id, name, central_bank, flag_url)
VALUES ('5', 'CANADA', 'BOC', 'https://cdn.countryflags.com/thumbs/canada/flag-800.png');
INSERT INTO countries (id, name, central_bank, flag_url)
VALUES ('6', 'AUSTRALIA', 'RBA', 'https://cdn.countryflags.com/thumbs/australia/flag-800.png');
INSERT INTO countries (id, name, central_bank, flag_url)
VALUES ('7', 'NZ', 'RBNZ', 'https://cdn.countryflags.com/thumbs/new-zealand/flag-800.png');
INSERT INTO countries (id, name, central_bank, flag_url)
VALUES ('8', 'NORWAY', 'CBN', 'https://cdn.countryflags.com/thumbs/norway/flag-800.png');
INSERT INTO countries (id, name, central_bank, flag_url)
VALUES ('9', 'SWEDEN', 'SNB', 'https://cdn.countryflags.com/thumbs/sweden/flag-800.png');
INSERT INTO countries (id, name, central_bank, flag_url)
VALUES ('10', 'SWITZERLAND', 'SRB',
        'https://www.countryflags.com/wp-content/uploads/switzerland-flag-png-large.png');


-- currency
INSERT INTO currencies (id, name, code, country_id)
VALUES (1, 'United States dollar', 'USD', 1);
INSERT INTO currencies (id, name, code, country_id)
VALUES (2, 'Japanese yen', 'JPY', 2);
INSERT INTO currencies (id, name, code, country_id)
VALUES (3, 'Pound sterling', 'GBP', 3);
INSERT INTO currencies (id, name, code, country_id)
VALUES (4, 'Euro', 'EUR', 4);
INSERT INTO currencies (id, name, code, country_id)
VALUES (5, 'Canadian dollar', 'CAD', 5);
INSERT INTO currencies (id, name, code, country_id)
VALUES (6, 'Australian dollar', 'AUD', 6);
INSERT INTO currencies (id, name, code, country_id)
VALUES (7, 'New Zealand dollar', 'NZD', 7);
INSERT INTO currencies (id, name, code, country_id)
VALUES (8, 'Norwegian krone', 'NOK', 8);
INSERT INTO currencies (id, name, code, country_id)
VALUES (9, 'Swedish krona', 'SEK', 9);
INSERT INTO currencies (id, name, code, country_id)
VALUES (10, 'Swiss franc', 'CHF', 10);

--currency pairs
INSERT INTO currency_pairs (id, name, base_currency_id, quote_currency_id)
VALUES (1, 'EURUSD', 4, 1);
INSERT INTO currency_pairs (id, name, base_currency_id, quote_currency_id)
VALUES (2, 'GBPUSD', 3, 1);
INSERT INTO currency_pairs (id, name, base_currency_id, quote_currency_id)
VALUES (3, 'USDJPY', 1, 2);
INSERT INTO currency_pairs (id, name, base_currency_id, quote_currency_id)
VALUES (4, 'USDCHF', 1, 10);
INSERT INTO currency_pairs (id, name, base_currency_id, quote_currency_id)
VALUES (5, 'USDSEK', 1, 9);
INSERT INTO currency_pairs (id, name, base_currency_id, quote_currency_id)
VALUES (6, 'USDNOK', 1, 8);
INSERT INTO currency_pairs (id, name, base_currency_id, quote_currency_id)
VALUES (7, 'USDCAD', 1, 5);
INSERT INTO currency_pairs (id, name, base_currency_id, quote_currency_id)
VALUES (8, 'AUDUSD', 6, 1);
INSERT INTO currency_pairs (id, name, base_currency_id, quote_currency_id)
VALUES (9, 'NZDUSD', 7, 1);

--trading rules
INSERT INTO trading_rules (entry_point, exit_point, take_profit, stop_loss)
VALUES ('Put pending order with parameters: byu-stop 21 pips and sell-stop 20 pips.
 Time: 2 minutes before publication of the economic indicator.
 Don`t delete non-active pending order during first 3 minutes.',
        'Apply Alligator with parameters:
    TimeFrame - 5 minutes,
    Price - Median price(HL/2),
    Jaw Time Period - 15,
    Shift - 0.
Don`t close position during first 5 minutes.
    The rules of close is valid after first 5 min candle.',
        100, 25);

INSERT INTO trading_rules (entry_point, exit_point, take_profit, stop_loss)
VALUES ('Rule for Norges Bank regional network Survey', 'ExitRule for Norges Bank regional network Survey', 1000, 450);

INSERT INTO trading_rules (entry_point, exit_point, take_profit, stop_loss)
VALUES ('Rule for PressConference', 'ExitRule PressConference', 110, 23);


-- economic Indicators
INSERT INTO economic_indicators (indicator, description, periodicity, country_id, currency_pair_id, trading_rule_id)
VALUES ('INTEREST_RATE', 'Federal Open Market Committee (FOMC) members vote on where to set the rate.
Traders watch interest rate changes closely as short term interest rates are the primary factor in currency valuation.
A higher than expected rate is positive/bullish for the USD, while a lower than expected rate is negative/bearish for the USD.',
        'MONTHLY', 1, 9, 1);

INSERT INTO economic_indicators (indicator, description, periodicity, country_id, currency_pair_id, trading_rule_id)
VALUES ('NBRNS', 'Norges Bank surveys executives from over 300 enterprises and organisations about recent economic developments and the outlook ahead.
The Bank’s interviewers normally visit contacts on-site for face-to-face discussions. A total of almost 1500 individuals are interviewed by the Bank approximately once a year.',
        'QUARTERLY', 8, 6, 2);

INSERT INTO economic_indicators (indicator, description, periodicity, country_id, currency_pair_id, trading_rule_id)
VALUES ('SPEECH', 'As head of the central bank, which sets short term interest rates, she has a major influence over the value of the currency.
                            Traders watch her speeches closely as they are often used to drop subtle hints regarding future monetary policy and interest rate shifts.
                            His comments may determine a short-term positive or negative trend.',
        'RANDOM', 4, 1, 3);

-- positions
INSERT INTO positions (trade, open_time, close_time, open_price, close_price, financial_result, yield, video_url,
                       economic_indicator_id, trader_id)
VALUES ('BUY', '2019-06-19 18:02:58', '2019-06-20 12:45:06', 0.65504, '0.65812', 5801, 11.35736, 'zvDh5ZP2_u4', 1, 2);
INSERT INTO positions (trade, open_time, close_time, open_price, close_price, financial_result, yield, video_url,
                       economic_indicator_id, trader_id)
VALUES ('SELL', '2019-06-11 08:00:45', '2019-06-11 13:50:06', 8.65612, 8.62913, 3583, 8.15745, 'JLR1k8eHh8w', 2, 2);
INSERT INTO positions (trade, open_time, close_time, open_price, close_price, financial_result, yield, video_url,
                       economic_indicator_id, trader_id)
VALUES ('SELL', '2019-06-18 08:01:12', '2018-06-18 10:25:01', 1.12229, 1.11929, 3297, 7.28103, 'r-gsEEZHYv8', 3, 2);
INSERT INTO positions (trade, open_time, close_time, open_price, close_price, financial_result, yield, video_url,
                       economic_indicator_id, trader_id)
VALUES ('BUY', '2019-06-19 18:02:58', '2019-06-20 12:45:06', 0.65524, 0.65812, 100, 3.44567, 'zvDh5ZP2_u4', 1, 3);
INSERT INTO positions (trade, open_time, close_time, open_price, close_price, financial_result, yield, video_url,
                       economic_indicator_id, trader_id)
VALUES ('SELL', '2019-06-11 08:00:45', '2019-06-11 13:50:16', 8.65612, 8.62913, 200, 2.2, 'JLR1k8eHh8w', 2, 3);
INSERT INTO positions (trade, open_time, close_time, open_price, close_price, financial_result, yield, video_url,
                       economic_indicator_id, trader_id)
VALUES ('SELL', '2019-06-18 08:01:12', '2018-06-18 10:23:01', 1.12239, 1.11929, 300, 4.5, 'r-gsEEZHYv8', 3, 3);

-- pictures
INSERT INTO pictures(url, public_id,position_id, trader_id)
VALUES ('https://res.cloudinary.com/drapmo8cx/image/upload/v1637921162/positions/2019-06-19_-_NZDUSD-_buy_gsns7q.jpg',
        '2019-06-19_-_NZDUSD-_buy_gsns7q', 1, 2);
INSERT INTO pictures(url, public_id,position_id, trader_id)
VALUES ('https://res.cloudinary.com/drapmo8cx/image/upload/v1637921315/positions/2019-06-11_-_USDNOK-_sell_x5tvgj.jpg',
        '2019-06-11_-_USDNOK-_sell_x5tvgj',2, 2);
INSERT INTO pictures(url, public_id,position_id, trader_id)
VALUES ('https://res.cloudinary.com/drapmo8cx/image/upload/v1637921630/positions/2019-06-18_-_eurusd_-_sell_ayikbn.jpg',
        '2019-06-18_-_eurusd_-_sell_ayikbn',3, 2);
INSERT INTO pictures(url, public_id,position_id, trader_id)
VALUES ('https://res.cloudinary.com/drapmo8cx/image/upload/v1637921162/positions/2019-06-19_-_NZDUSD-_buy_gsns7q.jpg',
        '2019-06-19_-_NZDUSD-_buy_gsns7q',4, 3);
INSERT INTO pictures(url, public_id,position_id, trader_id)
VALUES ('https://res.cloudinary.com/drapmo8cx/image/upload/v1637921315/positions/2019-06-11_-_USDNOK-_sell_x5tvgj.jpg',
        '2019-06-11_-_USDNOK-_sell_x5tvgj', 5, 3);
INSERT INTO pictures(url, public_id,position_id, trader_id)
VALUES ('https://res.cloudinary.com/drapmo8cx/image/upload/v1637921630/positions/2019-06-18_-_eurusd_-_sell_ayikbn.jpg',
        '2019-06-18_-_eurusd_-_sell_ayikbn',6, 3);

--Comments

INSERT INTO comments (approved, created, text_content, author_id, position_id)
VALUES (1, '2020-06-11 08:00:45', 'Comment number 1', 1, 1);
INSERT INTO comments (approved, created, text_content, author_id, position_id)
VALUES (1, '2020-07-11 08:00:45', 'Comment number 2', 2, 1);
INSERT INTO comments (approved, created, text_content, author_id, position_id)
VALUES (1, '2020-08-11 08:00:45', 'Comment number 3', 3, 1);
INSERT INTO comments (approved, created, text_content, author_id, position_id)
VALUES (1, '2020-09-11 08:00:45', 'Comment number 4', 4, 1);
INSERT INTO comments (approved, created, text_content, author_id, position_id)
VALUES (1, '2020-11-11 08:00:45', 'Comment number 5', 5, 1);
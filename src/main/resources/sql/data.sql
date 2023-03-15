-- -- MUNICIPALITY
insert into municipality(name) values ('Antwerp');
insert into municipality(name) values ('Brussels');

-- YOUTH COUNCIL
insert into youth_council (logo,name, municipality_id) values ('logo','Antwerp YC', 1);
insert into youth_council (logo,name, municipality_id) values ('logo','Brussels YC', 2);


-- -- WEBPAGE
-- insert into webpage values (1,True,True,True,True,True,True,'webpage AYC',1);
-- -- QUESTION + QUESTIONNAIRE
-- insert into question (id,question) values (1,'Are pie charts good?');
-- insert into questionnaire values (1, '2022-05-12 12:55:44', 'Answer truthfully', 'Antwerp Very Important Questionnaire', 1);
-- update question set questionnaire_id = 1 where id = 1;
-- -- STYLE
-- insert into style values (1,'ARIAL','RED','BLACK');
--
-- -- THEME
--
insert into theme (name) values ('Education');
--
-- -- STANDARD ACTION
--
-- insert into standard_action values (1,'Grants',1);
-- insert into standard_action values (2,'Study Spaces',1);
--
-- -- SOCIAL MEDIA LINKS
--
-- insert into social_media_link values (1,'https://www.facebook.com/antwerpyouthcouncil',2,1);
--
-- -- SECTION
--
-- insert into section values (1, 'This is a section of the landing page this is some text', 'Bring back AH pesto!','image link');
--
-- -- POST CODE
--
-- insert into postcodes values (1,2000);
--
-- -- NEWS ITEM
--
-- insert into news_item values (1,'Deliveroo discount for students!', 'image', 'Deliveroo Discount', 1);
--
-- USER
insert into app_user(email, first_name, last_name, password, post_code, username) values ('john@gmail.com','john','smith','$2a$10$9JUQf0FL3LqhbEmJU8LiVeMyBvaCUteI7fHfxkQxO0U/MEvAT2cKW','2000', 'john_smith');
-- password is 'user' (no quotation marks)

--
-- MEMBERSHIP
insert into membership values ('2022-05-12 12:55:44', 'USER', 1, 1);
insert into membership values ('2022-05-13 12:55:44', 'USER', 1, 2);

-- -- ACTION POINT
--
-- insert into action_point values(1,'2022-05-05 12:55:44','We need to do something regarding this really important thing!',5,1,'Very Important Action Point!','video link',1,1);
-- -- ACTIVITY
--
-- insert into activity values (1,'This is an activity, Lets move!','2023-06-05 12:55:44','Title! Running and moving','2023-06-02 12:55:44',1);
--
-- -- IDEA
--
insert into idea (created_date, description, author_id, theme_id, youth_council_id) values ('2023-06-05 12:55:44','Title! Running and moving',1,1,1);
insert into idea (created_date, description, author_id, theme_id, youth_council_id) values ('2023-06-06 12:55:44','Mandatory 20/20 for all exams',1,1,1);
insert into idea (created_date, description, author_id, theme_id, youth_council_id) values ('2023-06-06 12:55:44','Free computers for students',1,1,2);

--
-- -- COMMENT
--
-- insert into comment values (1,'This is a comment',1,1);
--
-- -- ANSWER
--
-- insert into answer values (1,'Yes',1);
--
-- -- UPDATE YC
-- update youth_council set style_id = 1 where id = 1;
-- update youth_council set home_page_id = 1 where id = 1;
-- update youth_council set municipality_municipality_id = 1 where id = 1;
--update youth_council set questionnaire_id = 1 where id = 1;
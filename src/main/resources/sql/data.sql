-- -- MUNICIPALITY
insert into municipality(name) values ('Antwerp');
insert into municipality(name) values ('Brussels');

-- YOUTH COUNCIL
insert into youth_council (slug, logo,name, municipality_id) values ('antwerpyouthcouncil', 'logo','Antwerp YC', 1);
insert into youth_council (slug, logo,name, municipality_id) values ('brusselsyouthcouncil', 'logo','Brussels YC', 2);


-- WEBPAGE
insert into webpage (action_points_enabled,activities_enabled,call_for_ideas_enabled,
                     call_to_complete_questionnaire_enabled,election_information_enabled,news_items_enabled, title,
                     youth_council_id)
values (True,True,
                                                                                                        True,True,True,True,'AYC - YCP',1);
update youth_council set home_page_id = 1 where id = 1;
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
insert into theme (name) values ('Environment');
--
-- -- STANDARD ACTION
--
insert into standard_action(name, theme_id) values ('Grants',1);
insert into standard_action(name, theme_id) values ('Study Spaces',1);
insert into standard_action(name, theme_id) values ('Tutoring',1);
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
-- NEWS ITEM

insert into news_item values (1,'Deliveroo discount for students!', 'image', 'Deliveroo Discount', 1);

-- USER
insert into app_user(email, first_name, last_name, password, post_code, username, is_general_admin) values ('john@gmail.com','john','smith','$2a$10$9JUQf0FL3LqhbEmJU8LiVeMyBvaCUteI7fHfxkQxO0U/MEvAT2cKW','2000', 'john_smith', false);
-- password is 'user' (no quotation marks)
insert into app_user(email, first_name, last_name, password, post_code, username, is_general_admin) values ('admina@gmail.com','admina','jones','$2a$10$uooPO89j22.ZBYdZ5MWK8.eFUKH7o01eBaYdwAiMKgpoItHSi8uv.','2060', 'admina1234', false);
--

insert into app_user(email, first_name, last_name, password, post_code, username, is_general_admin) values ('lars@kdg.be','lars','willemsens','$2a$10$18EoK.5JsBfwnm7.4MYmqu9kYQa1fqRf66ZjIKZS2dSHpvZJMuiSa','2000', 'lars', true);
-- password is 'lars'

insert into app_user(email, first_name, last_name, password, post_code, username, is_general_admin) values ('dieter@kdg.be','dieter','deleeuw','$2a$10$uRW4vVTVzhns5gkCSD7q0ukgm71D2buManijLB/NqTA5bqVapOn9u','2000', 'dieter', false);
-- password is 'company'

insert into app_user(email, first_name, last_name, password, post_code, username, is_general_admin) values ('piet@kdg.be','piet','boedt','$2a$10$8CvvFrOvK1.IK1lw.tD3NeBHuC87ciHLsid8lMqth2B6Hy3UkXi0q','2000', 'piet', false);
-- password is 'bash'

-- MEMBERSHIP
insert into membership values ('2022-05-12 12:55:44', 'USER', (SELECT user_id FROM app_user WHERE username = 'john_smith'), (SELECT id FROM youth_council WHERE name = 'Antwerp YC'));
insert into membership values ('2022-05-12 12:55:44', 'USER', (SELECT user_id FROM app_user WHERE username = 'john_smith'), (SELECT id FROM youth_council WHERE name = 'Brussels YC'));
insert into membership values ('2022-05-12 12:55:44', 'USER', (SELECT user_id FROM app_user WHERE username = 'piet'), (SELECT id FROM youth_council WHERE name = 'Antwerp YC'));
insert into membership values ('2022-05-12 12:55:44', 'YOUTH_COUNCIL_ADMIN', (SELECT user_id FROM app_user WHERE username = 'admina1234'), (SELECT id FROM youth_council WHERE name = 'Antwerp YC'));
insert into membership values ('2022-05-12 12:55:44', 'USER', (SELECT user_id FROM app_user WHERE username = 'dieter'), (SELECT id FROM youth_council WHERE name = 'Brussels YC'));



-- -- ACTION POINT
--
insert into action_point(created_date, description, status, title, video, standardaction_id, youth_council_id)
values ('2022-05-05 12:55:44','Increase funding for more study spaces to Antwerp universities', 'IN_PROGRESS','University study spaces','video link',1,1);

insert into action_point(created_date, description, status, title, video, standardaction_id, youth_council_id)
values ('2022-06-05 12:55:44','Create tutoring program for Antwerp middle school students', 'IN_PROGRESS','Student tutoring','www.youtube.com/123iuo12iu',3,1);

-- -- ACTIVITY
--
-- insert into activity values (1,'This is an activity, Lets move!','2023-06-05 12:55:44','Title! Running and moving','2023-06-02 12:55:44',1);
--
-- -- IDEA
--

insert into idea (created_date, description, author_id, theme_id, youth_council_id) values ('2023-06-06 12:55:44','Karel de Grote should add more study spaces. My friends and I are unable to find free spaces to study during exams.',(SELECT user_id FROM app_user WHERE username = 'piet'),1,(SELECT id FROM youth_council WHERE name = 'Antwerp YC'));
insert into idea (created_date, description, author_id, theme_id, youth_council_id) values ('2023-06-06 12:55:44','The University of Antwerp needs more study spaces. The library is always fully booked and nothing is available.',(SELECT user_id FROM app_user WHERE username = 'john_smith'),1,(SELECT id FROM youth_council WHERE name = 'Antwerp YC'));
insert into idea (created_date, description, author_id, theme_id, youth_council_id) values ('2023-06-05 12:55:44','Plant more trees in Parc Cinquantenaire',(SELECT user_id FROM app_user WHERE username = 'john_smith'),2,(SELECT id FROM youth_council WHERE name = 'Brussels YC'));

-- -- IDEA IMAGES

insert into idea_image values (3, 'tree.jpg');
insert into idea_image values (1, 'kdg.jpg');
insert into idea_image values (1, 'kdg2.jpg');
insert into idea_image values (2, 'antwerp.jpg');


-- -- ACTION POINT LINKED IDEAS

insert into action_points_linked_ideas values (1, 1);
insert into action_points_linked_ideas values (1, 2);

-- -- ACTION POINT IMAGES

insert into action_point_image values (1, 'image1.jpg');
insert into action_point_image values (1, 'image2.jpg');
insert into action_point_image values (1, 'image3.jpg');

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
-- update tenants set style_id = 1 where id = 1;
-- update tenants set home_page_id = 1 where id = 1;
-- update tenants set municipality_municipality_id = 1 where id = 1;
-- update tenants set questionnaire_id = 1 where id = 1;
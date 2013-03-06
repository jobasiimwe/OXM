INSERT INTO district (id, name, record_status) VALUES 
  ('6DE04CC6-61C1-4646-AFC0-5633D8F31CE8','Kampala',0),
  ('CC4C2FD2-8460-4bf9-AFEC-FC088E9FC9B7','Lamwo',0),
  ('9F802261-EE55-4550-B9AD-A88987D38307','Kitgum',0);



INSERT INTO sub_county (id, name, record_status, district_id) VALUES 
  ('F7A2752A-A7FD-4d04-B4CE-BFE808BA1D50','Omiya Anyima',0,'9F802261-EE55-4550-B9AD-A88987D38307'),
  ('8CCD0687-C5FF-43b9-82DE-EF592C65BA1D','Lagoro',0,'9F802261-EE55-4550-B9AD-A88987D38307'),
  ('33E68EB7-4F58-47ab-BDF3-00A35A66E91D','Akwang',0,'9F802261-EE55-4550-B9AD-A88987D38307');


INSERT INTO sub_county (id, name, record_status, district_id) VALUES 
  ('1E495B6E-101A-484d-8AF3-30D2A8BF07DC','Palabek Ogili',0,'CC4C2FD2-8460-4bf9-AFEC-FC088E9FC9B7'),
  ('FCE4C519-8F88-4df0-A04B-3F85C17EE19D','Palabek Gem',0,'CC4C2FD2-8460-4bf9-AFEC-FC088E9FC9B7'),
  ('120651B2-17FF-4cc6-B130-EA22864689AC','Lokung',0,'CC4C2FD2-8460-4bf9-AFEC-FC088E9FC9B7');

 

INSERT INTO parish (id, name, record_status, sub_county_id) VALUES 
  ('9CC7325E-2521-4172-B246-3F03EC90FECC','Melong',0,'F7A2752A-A7FD-4d04-B4CE-BFE808BA1D50'),
  ('1717F01E-8AE9-415c-9DE8-9869EDB70E93','Lakwor',0,'F7A2752A-A7FD-4d04-B4CE-BFE808BA1D50'),
  ('91A17CBF-2ED8-42dd-ABB6-F4908DFABE05','Lalano',0,'F7A2752A-A7FD-4d04-B4CE-BFE808BA1D50'),
  
  ('43E6B63C-E7A8-4e78-A9D6-509796F2157E','Lugwar',0,'1E495B6E-101A-484d-8AF3-30D2A8BF07DC'),
  ('0E579D6F-C2FC-417b-8906-6F5D55B6297D','Apyetta',0,'1E495B6E-101A-484d-8AF3-30D2A8BF07DC'),
  ('EF9A7732-2889-4e79-B810-03E7BFD42670','Padwat',0,'1E495B6E-101A-484d-8AF3-30D2A8BF07DC'),
  ('4375EEA4-BDE6-4a62-B38D-E74A3681D1D0','Paracelle',0,'1E495B6E-101A-484d-8AF3-30D2A8BF07DC');
 
  INSERT INTO parish (id, name, record_status, sub_county_id) VALUES 
  ('656827FB-FA42-4e74-B7B3-E27AE428A69D','Patanga',0,'FCE4C519-8F88-4df0-A04B-3F85C17EE19D'),
  ('E2DAED6E-CF4A-47b9-9342-589A383AAC85','Anaka',0,'FCE4C519-8F88-4df0-A04B-3F85C17EE19D'),
  ('CA321F45-6D57-4f6f-9A48-ED5EF2AD7CAA','Cubu',0,'FCE4C519-8F88-4df0-A04B-3F85C17EE19D'),
  ('70F81161-13DD-41a6-9BB7-647893211EC5','Moroto',0,'FCE4C519-8F88-4df0-A04B-3F85C17EE19D'),
  ('0F7EFB35-E907-444a-8997-1FFCD76E6D02','Gen',0,'FCE4C519-8F88-4df0-A04B-3F85C17EE19D'),
  
  ('C5AD79E0-E003-4093-BCCA-4349BA46D484','Lela Pwot',0,'120651B2-17FF-4cc6-B130-EA22864689AC'),
  ('DE2479F6-F903-4798-B295-A73BDF6AA1B8','Pakala Bule',0,'120651B2-17FF-4cc6-B130-EA22864689AC'),
  ('C086A98B-8B16-4523-ADB2-59728BB89BC2','Olebi',0,'120651B2-17FF-4cc6-B130-EA22864689AC'),
  ('7BB4A192-5B63-449a-9D76-184F3B563E88','Pangira',0,'120651B2-17FF-4cc6-B130-EA22864689AC'),
  ('12DE9C70-2FD6-4b77-845A-BCB911C825A0','Pachwa',0,'120651B2-17FF-4cc6-B130-EA22864689AC'),
  ('649664A6-2857-48a3-8C46-737ADEEFE933','Parapono',0,'120651B2-17FF-4cc6-B130-EA22864689AC'),
  ('75F98F8F-1707-4375-9E79-DF7A949C10BD','Pobel',0,'120651B2-17FF-4cc6-B130-EA22864689AC');



INSERT INTO village (id, name, record_status, parish_id) VALUES  
  ('7252A285-7849-4884-A7BC-422A8A6E538F','Tedo Pe',0,'C5AD79E0-E003-4093-BCCA-4349BA46D484'),
  ('C8767503-BE90-4d8d-9ECC-9798F5809104','Lumodo',0,'DE2479F6-F903-4798-B295-A73BDF6AA1B8'),
  ('BBAB3AEB-CF9A-4dfa-9A58-D0AAC37B2BD1','Okokwene',0,'C086A98B-8B16-4523-ADB2-59728BB89BC2'),
  ('E5C4FCB1-7391-46c6-BFDC-E3B6DAEE4EE5','Akeli Kong',0,'7BB4A192-5B63-449a-9D76-184F3B563E88'),
  ('AC45C8BD-C354-486c-A7F0-D7391005ECAB','Okora East/West',0,'12DE9C70-2FD6-4b77-845A-BCB911C825A0'),
  ('A4E42724-A755-4218-8441-F79801A3D337','Guria East',0,'649664A6-2857-48a3-8C46-737ADEEFE933'),
  ('96C7E0EF-4DAF-4707-8702-AD1F7BA5A5A5','Pobel Central',0,'75F98F8F-1707-4375-9E79-DF7A949C10BD'),
  ('35105D69-630C-4c75-8C23-C147E13E36DB','Pobel',0,'75F98F8F-1707-4375-9E79-DF7A949C10BD');



INSERT INTO village (id, name, record_status, parish_id) VALUES  
  ('1B15DA48-1B47-46fb-9A4C-2E06D15170BF','Kafufta',0,'656827FB-FA42-4e74-B7B3-E27AE428A69D'),
  ('EE55C499-F8BB-4322-A3E8-41B16CC7974F','Ayuu Lapur',0,'E2DAED6E-CF4A-47b9-9342-589A383AAC85'),
  ('F97085AD-11B9-41ee-8A5B-9A09D2462462','Anaka South',0,'E2DAED6E-CF4A-47b9-9342-589A383AAC85'),
  ('C1166767-1725-48fd-9DE1-DAF830E3C687','Layamo',0,'CA321F45-6D57-4f6f-9A48-ED5EF2AD7CAA'),
  ('8C0953BB-6EFB-47a4-B891-F4EDA4EF6566','Kafuta',0,'656827FB-FA42-4e74-B7B3-E27AE428A69D'),
  ('9562A7F1-832A-4d3b-A830-168EB445646A','Dyang Bii',0,'656827FB-FA42-4e74-B7B3-E27AE428A69D'),
  ('91C87C4E-1478-428d-B7EB-2B0837D99B35','Mixed Village',0,'70F81161-13DD-41a6-9BB7-647893211EC5'),
  ('3637E99F-8F11-401d-9D51-E3547EA3F47D','Mororto East',0,'70F81161-13DD-41a6-9BB7-647893211EC5'),
  ('811EE8B5-775C-420e-AFB5-714FF53ABC5E','Mede South',0,'0F7EFB35-E907-444a-8997-1FFCD76E6D02');



INSERT INTO selling_place (id, name, district_id, record_status) VALUES 
  ('0B6F5074-F1BB-4488-A13C-B49880068735','Kalerwe','6DE04CC6-61C1-4646-AFC0-5633D8F31CE8',0),
  ('730D9BE6-D659-498e-A6E8-6B53B4080810','Owino','6DE04CC6-61C1-4646-AFC0-5633D8F31CE8',0),
  ('48A55A53-5E6B-4e51-8223-72ED008C0E2C','Nakawa','6DE04CC6-61C1-4646-AFC0-5633D8F31CE8',0),
  ('98146598-0B3D-4968-A6E8-B2660042D67E','Nakasero','6DE04CC6-61C1-4646-AFC0-5633D8F31CE8',0);


INSERT INTO place_selling_types (selling_place_id, concept_id) VALUES
	('0B6F5074-F1BB-4488-A13C-B49880068735','303DC446-78EB-49e2-B7B9-9E0F86BB0E1A'),
	('0B6F5074-F1BB-4488-A13C-B49880068735','DDD6A821-175F-40e5-8470-ED96AE2F2412'),
	
	('730D9BE6-D659-498e-A6E8-6B53B4080810','303DC446-78EB-49e2-B7B9-9E0F86BB0E1A'),
	('730D9BE6-D659-498e-A6E8-6B53B4080810','DDD6A821-175F-40e5-8470-ED96AE2F2412'),
	
	('48A55A53-5E6B-4e51-8223-72ED008C0E2C','303DC446-78EB-49e2-B7B9-9E0F86BB0E1A'),
	('48A55A53-5E6B-4e51-8223-72ED008C0E2C','DDD6A821-175F-40e5-8470-ED96AE2F2412'),
	
	('98146598-0B3D-4968-A6E8-B2660042D67E','303DC446-78EB-49e2-B7B9-9E0F86BB0E1A');


	

conn= new Mongo("ds135234.mlab.com:35234");
db.auth("heroku_kfvlgzs0","fp6tacarv3rnvr6v7oi05cqh8g");
db = conn.getDB("heroku_kfvlgzs0");

cursor = db.projects.find();
while ( cursor.hasNext() ) {
   printjson( cursor.next() );
}
db.projects.insert({"name":"name"});
db.projects.insert({"name": "test", "skills": {"test":"test1","test":"test1"}, "description": "test","yearFrom":2020,"yearTo":2021});

		// this.id = id;
		// this.name = name;
		// this.skills = skills;
		// this.description = description;
		// this.yearFrom = yearFrom;
		// this.yearTo = yearTo;
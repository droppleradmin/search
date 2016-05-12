import _mysql

def up():
	return _mysql.connect("beatlist.cbifx5hgposw.us-west-2.rds.amazonaws.com","blessing","temppassword",db="stage")
	
def ptptest(db):
	query = """SELECT * FROM user_listen
	LIMIT 8000000;"""

	#lookup table build
	db.query(query)
	result = db.store_result().fetch_row(0,0)
	lookup = {}
	print "query complete, building lookup hash table"
	for item in result:
		track = item[1]
		user = item [0]
		lookup[int(user)] = lookup.setdefault(user,[]).append(track)

	#p2p table build
	print "lookup table complete, building p2p matrix"
	p2p = {}
	for item in result:
		track = item[1]
		user = item [0]
		for listen in lookup[user]:
			if(item == listen): continue
			p2p[track+'|'+listen] = p2p.setdefault(track+'|'+listen, 0) + 1
	
	result = None
	lookup = None
	return p2p
	
def recommend(id, p2p):
	list = []
	for item in p2p.keys():
		if item[:len(str(id))] == id or item[-len(str(id)):] == id:
			if p2p[item] > 10: list.append((item, p2p[item]))
			
	return list
	
def scan(p2p):
	for item in p2p.keys():
		if p2p[item] > 1:
			print item + " Listens: " + str(p2p[item])
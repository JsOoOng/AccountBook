@Override
	public List<AccountBook> findAll() {
		try {
			
		String sql = "select * from accountbook";
		PreparedStatement ps = conn.prepareStatement(sql);

		ResultSet rs = ps.executeQuery();
		
		List<AccountBook> list = new ArrayList<AccountBook>(); //빈리스트 만들기
		while(rs.next()) {
			
			
			
			int id = rs.getInt("id");
			String type = rs.getString("type");
			int amount = rs.getInt("amount");
			String category = rs.getString("category");
			String date = rs.getString("date");
			
			AccountBook ab= new AccountBook(id, type, amount, category, date);
			list.add(ab);
		}
		rs.close();
		ps.close();
		return list;
		
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

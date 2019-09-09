package com.maywide.biz.inter.pojo.queTagByCustid;

import java.util.List;

import com.maywide.biz.inter.pojo.cmptask.CmpBaseResp;

public class CmpCustTag extends CmpBaseResp {

	private DataBean data;


	public DataBean getData() {
		return data;
	}

	public void setData(DataBean data) {
		this.data = data;
	}


	public static class DataBean {
		/**
		 * baseInfo :
		 * {"cashFbBalance":"0","custid":"3601439592","m1opiniontalkcnt":"0",
		 * "name":"蔡晓燕","yearSumFee":"7323.84"} habit :
		 * {"btvVodperiod":"0","hobby":[{"PARENTNAME":"兴趣特征","TAGID":"103",
		 * "TAGNAME":"中度新闻爱好者"},{"PARENTNAME":"兴趣特征","TAGID":"104","TAGNAME":
		 * "重度新闻爱好者"},{"PARENTNAME":"兴趣特征","TAGID":"105","TAGNAME":"中度综艺节目爱好者"},
		 * {"PARENTNAME":"兴趣特征","TAGID":"106","TAGNAME":"重度综艺节目爱好者"},{
		 * "PARENTNAME":"兴趣特征","TAGID":"109","TAGNAME":"生活节目爱好者"},{"PARENTNAME":
		 * "兴趣特征","TAGID":"112","TAGNAME":"法制节目爱好者"},{"PARENTNAME":"兴趣特征",
		 * "TAGID":"125","TAGNAME":"战争剧爱好者"},{"PARENTNAME":"兴趣特征","TAGID":"132",
		 * "TAGNAME":"历史传奇剧爱好者"}],"m1allwatchhrs":"88.37","m1avgqatchhrs":"2.85"
		 * ,"m1restwatchhrs":"28.18","m1workwatchhrs":"60.19","monavgSumFee":
		 * "78.89","mtvVodperiod":"0","opTime":"20130408","vodperiod":".01"}
		 */

		private BaseInfoBean baseInfo;
		private HabitBean habit;
		private ViewRecordBean viewRecord;
		

		public ViewRecordBean getViewRecord() {
			return viewRecord;
		}

		public void setViewRecord(ViewRecordBean viewRecord) {
			this.viewRecord = viewRecord;
		}

		public BaseInfoBean getBaseInfo() {
			return baseInfo;
		}

		public void setBaseInfo(BaseInfoBean baseInfo) {
			this.baseInfo = baseInfo;
		}

		public HabitBean getHabit() {
			return habit;
		}

		public void setHabit(HabitBean habit) {
			this.habit = habit;
		}

		public static class BaseInfoBean {
			/**
			 * cashFbBalance : 0 custid : 3601439592 m1opiniontalkcnt : 0 name :
			 * 蔡晓燕 yearSumFee : 7323.84
			 */

			private String cashFbBalance;
			private String custid;
			private String m1opiniontalkcnt;
			private String name;
			private String yearSumFee;

			public String getCashFbBalance() {
				return cashFbBalance;
			}

			public void setCashFbBalance(String cashFbBalance) {
				this.cashFbBalance = cashFbBalance;
			}

			public String getCustid() {
				return custid;
			}

			public void setCustid(String custid) {
				this.custid = custid;
			}

			public String getM1opiniontalkcnt() {
				return m1opiniontalkcnt;
			}

			public void setM1opiniontalkcnt(String m1opiniontalkcnt) {
				this.m1opiniontalkcnt = m1opiniontalkcnt;
			}

			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			public String getYearSumFee() {
				return yearSumFee;
			}

			public void setYearSumFee(String yearSumFee) {
				this.yearSumFee = yearSumFee;
			}
		}
		
		 public static class ViewRecordBean {
	            /**
	             * mTopChnls : 天津卫视,CCTV-8电视剧,CCTV-6电影
	             * mTopPrograms : 光影星播客,与虎为伴,西游记
	             * wTopChnls : CCTV-5体育,CCTV-6电影,东莞新闻综合
	             * wTopPrograms : 家有儿女,奥运会女子击剑,亚洲运动会
	             */

	            private String mTopChnls;
	            private String mTopPrograms;
	            private String wTopChnls;
	            private String wTopPrograms;

	            public String getMTopChnls() {
	                return mTopChnls;
	            }

	            public void setMTopChnls(String mTopChnls) {
	                this.mTopChnls = mTopChnls;
	            }

	            public String getMTopPrograms() {
	                return mTopPrograms;
	            }

	            public void setMTopPrograms(String mTopPrograms) {
	                this.mTopPrograms = mTopPrograms;
	            }

	            public String getWTopChnls() {
	                return wTopChnls;
	            }

	            public void setWTopChnls(String wTopChnls) {
	                this.wTopChnls = wTopChnls;
	            }

	            public String getWTopPrograms() {
	                return wTopPrograms;
	            }

	            public void setWTopPrograms(String wTopPrograms) {
	                this.wTopPrograms = wTopPrograms;
	            }
	        }

		public static class HabitBean {
			/**
			 * btvVodperiod : 0 hobby :
			 * [{"PARENTNAME":"兴趣特征","TAGID":"103","TAGNAME":"中度新闻爱好者"},{
			 * "PARENTNAME":"兴趣特征","TAGID":"104","TAGNAME":"重度新闻爱好者"},{
			 * "PARENTNAME":"兴趣特征","TAGID":"105","TAGNAME":"中度综艺节目爱好者"},{
			 * "PARENTNAME":"兴趣特征","TAGID":"106","TAGNAME":"重度综艺节目爱好者"},{
			 * "PARENTNAME":"兴趣特征","TAGID":"109","TAGNAME":"生活节目爱好者"},{
			 * "PARENTNAME":"兴趣特征","TAGID":"112","TAGNAME":"法制节目爱好者"},{
			 * "PARENTNAME":"兴趣特征","TAGID":"125","TAGNAME":"战争剧爱好者"},{
			 * "PARENTNAME":"兴趣特征","TAGID":"132","TAGNAME":"历史传奇剧爱好者"}]
			 * m1allwatchhrs : 88.37 m1avgqatchhrs : 2.85 m1restwatchhrs : 28.18
			 * m1workwatchhrs : 60.19 monavgSumFee : 78.89 mtvVodperiod : 0
			 * opTime : 20130408 vodperiod : .01
			 */

			private String btvVodperiod;
			private String m1allwatchhrs;
			private String m1avgqatchhrs;
			private String m1restwatchhrs;
			private String m1workwatchhrs;
			private String monavgSumFee;
			private String mtvVodperiod;
			private String opTime;
			private String vodperiod;
			private List<HobbyBean> hobby;

			public String getBtvVodperiod() {
				return btvVodperiod;
			}

			public void setBtvVodperiod(String btvVodperiod) {
				this.btvVodperiod = btvVodperiod;
			}

			public String getM1allwatchhrs() {
				return m1allwatchhrs;
			}

			public void setM1allwatchhrs(String m1allwatchhrs) {
				this.m1allwatchhrs = m1allwatchhrs;
			}

			public String getM1avgqatchhrs() {
				return m1avgqatchhrs;
			}

			public void setM1avgqatchhrs(String m1avgqatchhrs) {
				this.m1avgqatchhrs = m1avgqatchhrs;
			}

			public String getM1restwatchhrs() {
				return m1restwatchhrs;
			}

			public void setM1restwatchhrs(String m1restwatchhrs) {
				this.m1restwatchhrs = m1restwatchhrs;
			}

			public String getM1workwatchhrs() {
				return m1workwatchhrs;
			}

			public void setM1workwatchhrs(String m1workwatchhrs) {
				this.m1workwatchhrs = m1workwatchhrs;
			}

			public String getMonavgSumFee() {
				return monavgSumFee;
			}

			public void setMonavgSumFee(String monavgSumFee) {
				this.monavgSumFee = monavgSumFee;
			}

			public String getMtvVodperiod() {
				return mtvVodperiod;
			}

			public void setMtvVodperiod(String mtvVodperiod) {
				this.mtvVodperiod = mtvVodperiod;
			}

			public String getOpTime() {
				return opTime;
			}

			public void setOpTime(String opTime) {
				this.opTime = opTime;
			}

			public String getVodperiod() {
				return vodperiod;
			}

			public void setVodperiod(String vodperiod) {
				this.vodperiod = vodperiod;
			}

			public List<HobbyBean> getHobby() {
				return hobby;
			}

			public void setHobby(List<HobbyBean> hobby) {
				this.hobby = hobby;
			}

			public static class HobbyBean {
				/**
				 * PARENTNAME : 兴趣特征 TAGID : 103 TAGNAME : 中度新闻爱好者
				 */

				private String PARENTNAME;
				private String TAGID;
				private String TAGNAME;

				public String getPARENTNAME() {
					return PARENTNAME;
				}

				public void setPARENTNAME(String PARENTNAME) {
					this.PARENTNAME = PARENTNAME;
				}

				public String getTAGID() {
					return TAGID;
				}

				public void setTAGID(String TAGID) {
					this.TAGID = TAGID;
				}

				public String getTAGNAME() {
					return TAGNAME;
				}

				public void setTAGNAME(String TAGNAME) {
					this.TAGNAME = TAGNAME;
				}
			}
		}
	}
}

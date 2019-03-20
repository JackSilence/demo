目的: 在Spring框架下實現資料庫讀寫分離架構

機制:
1. 透過基於動態代理的Spring AOP在調用MyBaits的Mapper前動態切換DataSource, 並於調用後Restore DataSource.<br>
使用H2 In-memory DB

2. 指定aspectjweaver為javaagent, 以利用Load-Time Weaving機制, 避免代理模式只能攔截外部方法調用的缺點,<br>方便交易管理

# EssayJoke
内涵段子（封装库）

####AsyncTask执行过程
#####ececute()方法一调用就会去判断状态，如果状态不对就会抛异常，然后会把状态置为Running ，然后执行onPreExecute(), 开一个线程执行 doInBackground(),         doInBackground()执行完毕之后会利用Handler发送消息切换主线程中，然后执行onPostExecute()方法，最后把状态置为FINISHED。

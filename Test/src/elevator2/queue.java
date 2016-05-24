package elevator2;

public class queue {
	private request[] Req = new request[10000];
	private int rear = 0;
	public synchronized void append(String s,long t)
	{
		String[] ss = s.split("[(),]");
		if(ss[1].equals("FR"))
		{
			int floor = Integer.parseInt(ss[2]);
			if(floor>20||floor<1)
			{
				System.out.println("����¥�㲻����Ҫ������������");
			}
			else if((floor == 20 && ss[3].equals("UP"))||floor == 1 &&ss[3].equals("DOWN"))
			{
				System.out.println("���벻����Ҫ������������");
			}
			else
			{
				this.Req[rear] = new request(ss[1],floor,ss[3],t);
				rear++;
				if(rear == 10000)
				{
					System.out.println("�����������������");
					System.exit(0);
				}
			}
		}
		else if(ss[1].equals("ER"))
		{
			int floor = Integer.parseInt(ss[3]);
			if(floor>20||floor<1)
			{
				System.out.println("����¥�㲻����Ҫ������������");
			}
			else
			{
				this.Req[rear] = new request(ss[1],ss[2],floor,t);
				rear++;
				if(rear == 10000)
				{
					System.out.println("�����������������");
					System.exit(0);
				}
			}
		}
	}
	public synchronized void append(request req)
	{
		this.Req[rear] = req;
		rear++;
	}
	public synchronized request fetch(int i)
	{
		return this.Req[i];		
	}
	public synchronized boolean hasreq()
	{
		int i = 0;
		for(i = 0;i < rear;i++)
		{
			if(Req[i].getsta() == statereq.unfinish)
				break;
		}
		if(i == rear)
			return false;
		else
			return true;
	}
	public synchronized int getrear()
	{
		return rear;
	}
}

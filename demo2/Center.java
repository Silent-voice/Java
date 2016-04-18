package demo2;

import java.util.Random;
import java.util.concurrent.*;

/**
 ��������
 
 */
public class Center extends Thread {  
    private BlockingQueue<Waiter> waiters;
    private BlockingQueue<Customer> customers;
    private Random rand = new Random();
    private final static int PRODUCERSLEEPSEED = 3000;  
    private final static int CONSUMERSLEEPSEED = 100000;

    //�����ṩ����Ĺ�̨���к�ȡ�úŵĿͻ�����
    public Center(int num) {
        waiters = new LinkedBlockingDeque<>(num);/*�����̨���г���*/
        for(int i = 0; i < num; i++){
            waiters.add(new Waiter());
        }
        customers = new LinkedBlockingQueue<>();
    }  
    //ȡ�Ż������º���
    public void produce() throws InterruptedException {
        Customer customer = new Customer();
        customers.add(customer);
        System.out.println(customer.toString() + "�Ź˿����ڵȴ�����");
        TimeUnit.MILLISECONDS.sleep(rand.nextInt(PRODUCERSLEEPSEED));
    }
    //�ͻ���÷���
    public void consume() throws InterruptedException {
        Customer customer = customers.take();/*��ȡ���Ƴ��˿Ͷ��еĶ�ͷԪ��*/
        Waiter waiter = waiters.take();
        System.out.println(waiter.toString() + "�Ŵ�������Ϊ" + customer.toString() + "�Ź˿Ͱ���ҵ��");
        TimeUnit.MILLISECONDS.sleep(rand.nextInt(CONSUMERSLEEPSEED));/*����ʱ��*/
        waiters.add(waiter);
    }  
} 
package Wber;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 出租车类
 */
public class Car implements Runnable {
    private static AtomicInteger count = new AtomicInteger();
    private int num;
    private Location location;/*坐标*/
    private int credit = 0;/*信用度*/
    private CarState carState;/*运行状态*/
    private Point[][] points = new Point[80][80];/*地图*/
    private Location startLocation;/*乘客起点*/
    private Location destinationLocation;/*乘客终点*/
    private boolean hasPassenger = false;

    /**
     * 构造器
     */
    public Car(Point[][] points) {
        carState = CarState.Waiting;
        this.points = points.clone();
        location = new Location();
        count.addAndGet(1);
        num = count.get();
    }

    /**
     * 构造器2
     */
    public Car(Point[][] points, int x, int y){
        carState = CarState.Waiting;
        this.points = points.clone();
        location = new Location(x, y);
        num = count.addAndGet(1);
    }

    public void setHasPassenger(boolean hasPassenger) {
        this.hasPassenger = hasPassenger;
    }

    public boolean isHasPassenger() {
        return hasPassenger;
    }

    public void setStartLocation(Location startLocation) {
        this.startLocation = startLocation;
    }

    public void setDestinationLocation(Location destinationLocation) {
        this.destinationLocation = destinationLocation;
    }

    public Location getLocation() {
        return location.clone();
    }

    public void setLocation(int x, int y) {
        this.location.setXY(x, y);
    }

    public void addCredit(int i) {
        credit += i;
    }

    public int getCredit() {
        return credit;
    }

    public int getNum() {
        return num;
    }

    public void setCarState(CarState carState) {
        this.carState = carState;
    }

    public CarState getCarState() {
        return carState;
    }

    public String getCurrentTimeDate(){
        return new Date(System.currentTimeMillis()).toString();
    }

    public long getCurrentTimeLong(){
        return System.currentTimeMillis();
    }

    /**
     * 初始化地图
     */
    public void initPoints() {
        for (int i = 0; i < 80; i++) {
            for (int j = 0; j < 80; j++) {
                points[i][j].ifPreUsed = false;
                points[i][j].ifLastUsed = false;
            }
        }
    }

    /**
     * 单向广搜
     * @param startLocation 起始点
     * @param destinationLocation 目的地
     * @return 路径
     */
    public ArrayList<Integer> findPathSingle(Location startLocation, Location destinationLocation){
        initPoints();
        ArrayList<Integer> path = new ArrayList<>();
        Queue<Location> preQueue = new ArrayDeque<>();/*前序队列*/
        int[] records = new int[6400];
        int preSonLoc = -1;// = startLocation.oneDimensionalLoc();

        points[startLocation.getX()][startLocation.getY()].ifPreUsed = true;
        records[startLocation.oneDimensionalLoc()] = startLocation.oneDimensionalLoc();
        preQueue.add(startLocation);
        points[destinationLocation.getX()][destinationLocation.getY()].ifLastUsed = true;

        if (startLocation.oneDimensionalLoc() == destinationLocation.oneDimensionalLoc()) {/*起点与终点相同，直接返回*/
            return path;
        }

        while (true) {
            if (!preQueue.isEmpty()) {
                Location preLocation = preQueue.poll();
                Point point = points[preLocation.getX()][preLocation.getY()];
                if (point.up && inRange(preLocation.getX() - 1) && inRange(preLocation.getY())
                        && !points[preLocation.getX() - 1][preLocation.getY()].ifPreUsed) {
                    Location nextLocation = new Location(preLocation.getX() - 1, preLocation.getY());
                    Point nextPoint = points[nextLocation.getX()][nextLocation.getY()];
                    if (nextPoint.ifLastUsed) {
                        preSonLoc = preLocation.oneDimensionalLoc();
                        break;
                    }
                    if (!nextPoint.ifPreUsed) {/*未被前序走过*/
                        records[nextLocation.oneDimensionalLoc()] = preLocation.oneDimensionalLoc();
                        preQueue.add(nextLocation);
                        nextPoint.ifPreUsed = true;
//                        preSonLoc = nextLocation.oneDimensionalLoc();
                    }
                }
                if (point.down && inRange(preLocation.getX() + 1) && inRange(preLocation.getY())
                        && !points[preLocation.getX() + 1][preLocation.getY()].ifPreUsed) {
                    Location nextLocation = new Location(preLocation.getX() + 1, preLocation.getY());
                    Point nextPoint = points[nextLocation.getX()][nextLocation.getY()];
                    if (nextPoint.ifLastUsed) {
                        preSonLoc = preLocation.oneDimensionalLoc();
                        break;
                    }
                    if (!nextPoint.ifPreUsed) {/*未被前序走过*/
                        records[nextLocation.oneDimensionalLoc()] = preLocation.oneDimensionalLoc();
                        preQueue.add(nextLocation);
                        nextPoint.ifPreUsed = true;
//                        preSonLoc = nextLocation.oneDimensionalLoc();
                    }
                }
                if (point.left && inRange(preLocation.getX()) && inRange(preLocation.getY() - 1)
                        && !points[preLocation.getX()][preLocation.getY() - 1].ifPreUsed) {
                    Location nextLocation = new Location(preLocation.getX(), preLocation.getY() - 1);
                    Point nextPoint = points[nextLocation.getX()][nextLocation.getY()];
                    if (nextPoint.ifLastUsed) {
                        preSonLoc = preLocation.oneDimensionalLoc();
                        break;
                    }
                    if (!nextPoint.ifPreUsed) {/*未被前序走过*/
                        records[nextLocation.oneDimensionalLoc()] = preLocation.oneDimensionalLoc();
                        preQueue.add(nextLocation);
                        nextPoint.ifPreUsed = true;
//                        preSonLoc = nextLocation.oneDimensionalLoc();
                    }
                }
                if (point.right && inRange(preLocation.getX()) && inRange(preLocation.getY() + 1)
                        && !points[preLocation.getX()][preLocation.getY() + 1].ifPreUsed) {
                    Location nextLocation = new Location(preLocation.getX(), preLocation.getY() + 1);
                    Point nextPoint = points[nextLocation.getX()][nextLocation.getY()];
                    if (nextPoint.ifLastUsed) {
                        preSonLoc = preLocation.oneDimensionalLoc();
                        break;
                    }
                    if (!nextPoint.ifPreUsed) {/*未被前序走过*/
                        records[nextLocation.oneDimensionalLoc()] = preLocation.oneDimensionalLoc();
                        preQueue.add(nextLocation);
                        nextPoint.ifPreUsed = true;
//                        preSonLoc = nextLocation.oneDimensionalLoc();
                    }
                }
            }
        }

        Stack<Integer> backPath = new Stack<>();
        int parentLoc = preSonLoc;
        int childLoc;
        /*将前序队列加入路径*/
        do {
            backPath.push(parentLoc);
            childLoc = parentLoc;
            parentLoc = records[childLoc];
            if(backPath.size() > 6400) {
                System.out.println("startLocation " + startLocation.getX() + " " + startLocation.getY());
                System.out.println("destinationLocation " + destinationLocation.getX() + " " + destinationLocation.getY());
                System.out.println("parentLoc " + parentLoc);
                System.out.println("childLoc " + childLoc);
            }
        } while (parentLoc != childLoc);
        while (backPath.size() > 0) {
            path.add(backPath.pop());
        }
        path.remove(0);
        path.add(destinationLocation.oneDimensionalLoc());
        return path;
    }

    /**
     * 通过双向广搜获得最短路径
     * @param startLocation       初始坐标
     * @param destinationLocation 目的地
     * @return 最短路径
     */
    public ArrayList<Integer> findPath(Location startLocation, Location destinationLocation) {
        Point[][] point = points.clone();
        ArrayList<Integer> path = new ArrayList<>();
        Queue<Location> preQueue = new ArrayDeque<>();/*前序队列*/
        Queue<Location> lastQueue = new ArrayDeque<>();/*后序队列*/
        int[] records = new int[6400];
        int lastSonLoc = startLocation.oneDimensionalLoc();
        int preSonLoc = destinationLocation.oneDimensionalLoc();
        point[startLocation.getX()][startLocation.getY()].ifPreUsed = true;
        records[startLocation.oneDimensionalLoc()] = startLocation.oneDimensionalLoc();
        preQueue.add(startLocation);
        point[destinationLocation.getX()][destinationLocation.getY()].ifLastUsed = true;
        records[destinationLocation.oneDimensionalLoc()] = destinationLocation.oneDimensionalLoc();
        lastQueue.add(destinationLocation);

        if (startLocation.oneDimensionalLoc() == destinationLocation.oneDimensionalLoc()) {/*起点与终点相同，直接返回*/
            return path;
        }

        while (true) {
            if (!preQueue.isEmpty()) {
                Location preLocation = preQueue.poll();
                Point p = point[preLocation.getX()][preLocation.getY()];
                if (p.up && inRange(preLocation.getX() - 1) && inRange(preLocation.getY())
                        && !point[preLocation.getX() - 1][preLocation.getY()].ifPreUsed) {
                    Location nextLocation = new Location(preLocation.getX() - 1, preLocation.getY());
                    Point nextPoint = point[nextLocation.getX()][nextLocation.getY()];
                    if (nextPoint.ifLastUsed) {
                        preSonLoc = preLocation.oneDimensionalLoc();
                        lastSonLoc = nextLocation.oneDimensionalLoc();
                        break;
                    }
                    if (!nextPoint.ifPreUsed) {/*未被前序走过*/
                        records[nextLocation.oneDimensionalLoc()] = preLocation.oneDimensionalLoc();
                        preQueue.add(nextLocation);
                        nextPoint.ifPreUsed = true;
                    }
                }
                if (p.down && inRange(preLocation.getX() + 1) && inRange(preLocation.getY())
                        && !point[preLocation.getX() + 1][preLocation.getY()].ifPreUsed) {
                    Location nextLocation = new Location(preLocation.getX() + 1, preLocation.getY());
                    Point nextPoint = points[nextLocation.getX()][nextLocation.getY()];
                    if (nextPoint.ifLastUsed) {
                        preSonLoc = preLocation.oneDimensionalLoc();
                        lastSonLoc = nextLocation.oneDimensionalLoc();
                        break;
                    }
                    if (!nextPoint.ifPreUsed) {/*未被前序走过*/
                        records[nextLocation.oneDimensionalLoc()] = preLocation.oneDimensionalLoc();
                        preQueue.add(nextLocation);
                        nextPoint.ifPreUsed = true;
                    }
                }
                if (p.left && inRange(preLocation.getX()) && inRange(preLocation.getY() - 1)
                        && !point[preLocation.getX()][preLocation.getY() - 1].ifPreUsed) {
                    Location nextLocation = new Location(preLocation.getX(), preLocation.getY() - 1);
                    Point nextPoint = point[nextLocation.getX()][nextLocation.getY()];
                    if (nextPoint.ifLastUsed) {
                        preSonLoc = preLocation.oneDimensionalLoc();
                        lastSonLoc = nextLocation.oneDimensionalLoc();
                        break;
                    }
                    if (!nextPoint.ifPreUsed) {/*未被前序走过*/
                        records[nextLocation.oneDimensionalLoc()] = preLocation.oneDimensionalLoc();
                        preQueue.add(nextLocation);
                        nextPoint.ifPreUsed = true;
                    }
                }
                if (p.right && inRange(preLocation.getX()) && inRange(preLocation.getY() + 1)
                        && !point[preLocation.getX()][preLocation.getY() + 1].ifPreUsed) {
                    Location nextLocation = new Location(preLocation.getX(), preLocation.getY() + 1);
                    Point nextPoint = point[nextLocation.getX()][nextLocation.getY()];
                    if (nextPoint.ifLastUsed) {
                        preSonLoc = preLocation.oneDimensionalLoc();
                        lastSonLoc = nextLocation.oneDimensionalLoc();
                        break;
                    }
                    if (!nextPoint.ifPreUsed) {/*未被前序走过*/
                        records[nextLocation.oneDimensionalLoc()] = preLocation.oneDimensionalLoc();
                        preQueue.add(nextLocation);
                        nextPoint.ifPreUsed = true;
                    }
                }
            }

            if (!lastQueue.isEmpty()) {
                Location preLocation = lastQueue.poll();
                Point p = point[preLocation.getX()][preLocation.getY()];
                if (p.up && inRange(preLocation.getX() - 1) && inRange(preLocation.getY())
                        && !point[preLocation.getX() - 1][preLocation.getY()].ifLastUsed) {
                    Location nextLocation = new Location(preLocation.getX() - 1, preLocation.getY());
                    Point nextPoint = point[nextLocation.getX()][nextLocation.getY()];
                    if (nextPoint.ifPreUsed) {
                        lastSonLoc = preLocation.oneDimensionalLoc();
                        preSonLoc = nextLocation.oneDimensionalLoc();
                        break;
                    }
                    if (!nextPoint.ifLastUsed) {/*未被后序走过*/
                        records[nextLocation.oneDimensionalLoc()] = preLocation.oneDimensionalLoc();
                        lastQueue.add(nextLocation);
                        nextPoint.ifLastUsed = true;
                    }
                }
                if (p.down && inRange(preLocation.getX() + 1) && inRange(preLocation.getY())
                        && !point[preLocation.getX() + 1][preLocation.getY()].ifLastUsed) {
                    Location nextLocation = new Location(preLocation.getX() + 1, preLocation.getY());
                    Point nextPoint = point[nextLocation.getX()][nextLocation.getY()];
                    if (nextPoint.ifPreUsed) {
                        lastSonLoc = preLocation.oneDimensionalLoc();
                        preSonLoc = nextLocation.oneDimensionalLoc();
                        break;
                    }
                    if (!nextPoint.ifLastUsed) {/*未被后序走过*/
                        records[nextLocation.oneDimensionalLoc()] = preLocation.oneDimensionalLoc();
                        lastQueue.add(nextLocation);
                        nextPoint.ifLastUsed = true;
                    }
                }
                if (p.left && inRange(preLocation.getX()) && inRange(preLocation.getY() - 1)
                        && !point[preLocation.getX()][preLocation.getY() - 1].ifLastUsed) {
                    Location nextLocation = new Location(preLocation.getX(), preLocation.getY() - 1);
                    Point nextPoint = point[nextLocation.getX()][nextLocation.getY()];
                    if (nextPoint.ifPreUsed) {
                        lastSonLoc = preLocation.oneDimensionalLoc();
                        preSonLoc = nextLocation.oneDimensionalLoc();
                        break;
                    }
                    if (!nextPoint.ifLastUsed) {/*未被后序走过*/
                        records[nextLocation.oneDimensionalLoc()] = preLocation.oneDimensionalLoc();
                        lastQueue.add(nextLocation);
                        nextPoint.ifLastUsed = true;
                    }
                }
                if (p.right && inRange(preLocation.getX()) && inRange(preLocation.getY() + 1)
                        && !point[preLocation.getX()][preLocation.getY() + 1].ifLastUsed) {
                    Location nextLocation = new Location(preLocation.getX(), preLocation.getY() + 1);
                    Point nextPoint = point[nextLocation.getX()][nextLocation.getY()];
                    if (nextPoint.ifPreUsed) {
                        lastSonLoc = preLocation.oneDimensionalLoc();
                        preSonLoc = nextLocation.oneDimensionalLoc();
                        break;
                    }
                    if (!nextPoint.ifLastUsed) {/*未被后序走过*/
                        records[nextLocation.oneDimensionalLoc()] = preLocation.oneDimensionalLoc();
                        lastQueue.add(nextLocation);
                        nextPoint.ifLastUsed = true;
                    }
                }
            }

            if (preQueue.isEmpty() && lastQueue.isEmpty()) {
                break;
            }
        }

        Stack<Integer> backPath = new Stack<>();
        int parentLoc = preSonLoc;
        if(preSonLoc == -1) {
            System.out.println(startLocation.getX() + " " + startLocation.getY());
            System.out.println(destinationLocation.getX() + " " + destinationLocation.getY());
        }
//        backPath.push(parentLoc);
        int childLoc;
        boolean flag = false;

        /*将前序队列加入路径*/
        do {
            backPath.push(parentLoc);
            childLoc = parentLoc;
            parentLoc = records[childLoc];
            if(backPath.size() > 6400) {
                System.out.println("startLocation " + startLocation.getX() + " " + startLocation.getY());
                System.out.println("destinationLocation " + destinationLocation.getX() + " " + destinationLocation.getY());
                System.out.println("parentLoc " + parentLoc / 80 + " " + parentLoc % 80);
                System.out.println("childLoc " + childLoc);
                System.out.println(""+num+":"+records);
                break;
            }
//            if (records[records[parentLoc]] == parentLoc) {
//                flag = true;
//                break;
//            }
        } while (parentLoc != childLoc);

        while (backPath.size() > 0) {
            path.add(backPath.pop());
        }
        /*将后序队列加入路径*/
        parentLoc = lastSonLoc;
        do {
            path.add(parentLoc);
            childLoc = parentLoc;
            parentLoc = records[childLoc];
            if(backPath.size() > 6401) {
                System.out.println("startLocation " + startLocation.getX() + " " + startLocation.getY());
                System.out.println("destinationLocation " + destinationLocation.getX() + " " + destinationLocation.getY());
                System.out.println("parentLoc " + parentLoc / 80 + " " + parentLoc % 80);
                System.out.println("childLoc " + childLoc);
                break;
            }
//            if (records[records[parentLoc]] == parentLoc) {
//                flag = true;
//                break;
//            }
        } while (parentLoc != childLoc);
//        if(!flag)
        path.remove(0);/*去除第一个出租车所在的点*/
//        else
//            path.add(destinationLocation.oneDimensionalLoc());
//
//        if(flag){
//            synchronized (System.out) {
//                System.out.println("startLocation " + startLocation.getX() + " " + startLocation.getY());
//                System.out.println("destinationLocation " + destinationLocation.getX() + " " + destinationLocation.getY());
//                for (Integer anArrayList : path) {
//                    System.out.println(anArrayList / 80 + " " + anArrayList % 80);
//                }
//            }
//        }
        return path;
    }

    public boolean inRange(int num) {
        return 0 <= num && num <= 79;
    }

    /**
     * 出租车按照固定路线驶向指定目标
     * @param path 指定路线
     * @throws InterruptedException
     */
    public void moveToDestination(ArrayList<Integer> path) throws InterruptedException {
        for (int i = 0; i < path.size(); i++) {
            Thread.sleep(100);
            location.setXY(path.get(i) / 80, path.get(i) % 80);
        }
    }

    /**
     * 在等待状态下随机移动
     */
    public void waitingMove() throws InterruptedException {
        Random random = new Random();
        long startTime = System.currentTimeMillis();
        while (carState == CarState.Waiting) {
            if(location.getX() < 0 || location.getY() < 0)
                System.out.println("x " + location.getX() + " y " + location.getY());
            Point point = points[location.getX()][location.getY()];
            long endTime = System.currentTimeMillis();
            if (random.nextInt(4) == 0 && point.up) {
                moveUp();
            } else if (random.nextInt(4) == 1 && point.down) {
                moveDown();
            } else if (random.nextInt(4) == 2 && point.left) {
                moveLeft();
            } else if (random.nextInt(4) == 3 && point.right) {
                moveRight();
            }
            if (endTime - startTime > 20000) {/*运行时间到达20秒，停止1秒*/
                startTime = endTime;
                Thread.sleep(1000);
            }
        }
    }

    public void moveUp() throws InterruptedException {
        if(location.getX() -  1 < 0)
            return;
        location.setY(location.getX() - 1);
        Thread.sleep(100);
    }

    public void moveDown() throws InterruptedException {
        if(location.getX() + 1 >= 80)
            return;
        location.setY(location.getX() + 1);
        Thread.sleep(100);
    }

    public void moveLeft() throws InterruptedException {
        if(location.getY() - 1 < 0)
            return;
        location.setX(location.getY() - 1);
        Thread.sleep(100);
    }

    public void moveRight() throws InterruptedException {
        if (location.getY() + 1 >= 80)
            return;
        location.setX(location.getY() + 1);
        Thread.sleep(100);
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (carState == CarState.Waiting) {
                    waitingMove();
                } else if (carState == CarState.Stopping) {

                } else if (carState == CarState.Serving) {
                    moveToDestination(findPath(startLocation, destinationLocation));
                    System.out.println(num + " 号出租车到达目的地");
                    addCredit(3);/*完成订单，信用度加三*/
                    hasPassenger = false;
                    carState = CarState.Stopping;
                    Thread.sleep(1000);
                    carState = CarState.Waiting;
                } else if (carState == CarState.WaitServing) {
                    addCredit(1);/*抢单成功，信用度加一*/
                    moveToDestination(findPath(location, startLocation));
                    carState = CarState.Stopping;
                    Thread.sleep(1000);
                    carState = CarState.Serving;
                }

            }
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            System.out.println(num + " 号出租车停止运行");
            System.exit(0);
        }
    }
}

enum CarState{
    Waiting,/*等待状态*/
    Serving,/*服务状态*/
    Stopping,/*停止状态*/
    WaitServing/*等待服务状态*/
}
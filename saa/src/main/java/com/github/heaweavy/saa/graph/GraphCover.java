package com.github.heaweavy.saa.graph;


/**
 * 图的遍历
 */
public class GraphCover extends Graph{
    private int[] visit = new int[size];     //遍历标志，防止死环遍历

    /**
     * 深度优先遍历
     * 一条路走到黑,不撞南墙不回头
     * 对每一个可能的分支路径深入到不能再深入为止
     */
    public void DeepFirst(int start) {//从第n个节点开始遍历

        visit[start] = 1;              //标记为1表示该顶点已经被处理过
        System.out.println("齐天大圣到—>" + this.nodes[start]+"一游"); //输出节点数据

        for (int i=0;i<this.size;i++){
            if (this.edges[start][i] == 1 && visit[i]==0){
                //邻接点
                DeepFirst(i);
            }
        }

    }

    /**
     * 广度优先遍历
     * 广度优先搜索遍历图的过程中以v 为起始点，由近至远，
     * 依次访问和v 有路径相通且路径长度为1,2,…的顶点
     * 第一批节点的邻接点，?
     */
    private int[] queue = new int[size];
    public void BreadthFirst(int front,int tail) {
        int last = tail;

        for (int index=front;index<=tail;index++){
            int node = queue[index];

            System.out.println("齐天大圣到—>" + this.nodes[node]+"一游"); //输出节点数据
            //找出所有的邻接点
            for (int i=0;i<this.size;i++){
                if (this.edges[node][i] == 1 && visit[i]==0){
                    //邻接点
                    visit[i] = 1;
                    queue[++last] = i;

                }
            }
        }

        //遍历下一批节点
        if (last > tail){
            BreadthFirst(tail+1,last);
        }

    }

    public void BreadthFirst(int start){
        queue[0] = start;
        visit[start] = 1;
        BreadthFirst(0,0);
    }

    public static void main(String[] args) {
        GraphCover graph = new GraphCover();

        graph.BreadthFirst(0);
    }
}

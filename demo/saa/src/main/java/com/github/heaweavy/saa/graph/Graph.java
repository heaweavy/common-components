package com.github.heaweavy.saa.graph;



/*
 * 定义图的结构
 */
public class Graph {
    //节点数目
    protected int size;
    //定义数组，保存顶点信息
    protected String[] nodes;

    //定义矩阵保存顶点信息
    protected int[][] edges;

    /**
     *      A B C D E F G
     *   A  0 0 1 1 0 1 0
     *   B  0 0 1 0 0 0 0
     *   C  1 1 0 1 0 0 0
     *   D  1 0 1 0 0 0 0
     *   E  0 0 0 0 0 0 1
     *   F  1 0 0 0 0 0 1
     *   G  0 0 0 0 1 1 0
     */
    public Graph(){
        //初始化顶点
        nodes = new String[]{"A","B","C","D","E","F","G"};
        size=nodes.length;

        //初始化边---- 为了直观，做一个常量定义
        final int A=0,B=1,C=2,D=3,E=4,F=5,G=6;
        edges = new int[size][size];
        edges[A][C] = 1;
        edges[A][D] = 1;
        edges[A][F] = 1;
        edges[B][C] = 1;
        edges[C][A] = 1;
        edges[C][D] = 1;
        edges[C][B] = 1;
        edges[D][A] = 1;
        edges[D][C] = 1;
        edges[E][G] = 1;
        edges[F][A] = 1;
        edges[F][G] = 1;
        edges[G][F] = 1;
        edges[G][E] = 1;
    }

}

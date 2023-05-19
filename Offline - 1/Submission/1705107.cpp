#include<bits/stdc++.h>
using namespace std;

int k;
unsigned char** targetGrid;


/*int manhattanDistance(unsigned char** grid){
    int distance = 0;
    for(int i=0;i<k;i++){
        for(int j=0;j<k;j++){
            if(grid[i][j] == 0) continue;
            int x = (grid[i][j]-1)/k;
            int y = (grid[i][j]-1)%k;
            distance += abs(x-i) + abs(y-j);
        }
    }
    return distance;
}*/

int manhattanDistance(unsigned char** grid){
    int dist = 0;
    int x1, y1, x2, y2;
    int gridval;
    for(int i=0; i<k*k; i++){
        gridval = grid[i/k][i%k];
        if(gridval == 0) continue;
        x1 = i/k;
        y1 = i%k;
        x2 = (gridval-1)/k;
        y2 = (gridval-1)%k;
        dist += abs(x1-x2) + abs(y1-y2);
    }
    return dist;
}

int hammingDistance(unsigned char** grid) {
    int dist = 0;
    for(int i = 0; i < k; i++) {
        for(int j = 0; j < k; j++) {
            if(grid[i][j] != targetGrid[i][j]) {
                dist++;
            }
        }
    }
    if(grid[k-1][k-1]!=targetGrid[k-1][k-1]) {
        dist--;
    }
    return dist;
}



void print(unsigned char** g){
    for(int i=0;i<k;i++){
        for(int j=0;j<k;j++){
            if(g[i][j]==0) cout<<"   ";
            else {
                int x  = int(g[i][j]);
                cout<<setw(3)<<x;
            }
        }
        cout<<endl;
    }
    cout<<endl;
}

string mapIdxMaker(unsigned char** g){
    string s="";
    for(int i=0;i<k;i++){
        for(int j=0;j<k;j++){
            s+=g[i][j];
        }
    }
    return s;
}

unsigned char** copy(unsigned char** g){
    unsigned char** newGrid = new unsigned char*[k];
    for(int i=0;i<k;i++){
        newGrid[i] = new unsigned char[k];
        for(int j=0;j<k;j++){
            newGrid[i][j] = g[i][j];
        }
    }
    return newGrid;
}

void destruct(unsigned char** g){
    for(int i=0;i<k;i++){
        delete[] g[i];
    }
    delete[] g;
}



struct Node
{
    unsigned char** grid;
    int f_n, g_n;
    int move;
    // 0: up, 1: down, 2: left, 3: right
    Node* parent;
};

/*struct customComp
{
    bool operator()(Node* const& a, Node* const& b){
        if(a->f_n==b->f_n){
            return a->g_n < b->g_n;
        }
        return a->f_n > b->f_n;
    }
};*/

struct customComp
{
    bool operator()(Node* const& a, Node* const& b){
        if(a->f_n==b->f_n){
            return a->f_n-a->g_n > b->f_n-b->g_n;
        }
        return a->f_n > b->f_n;
    }
};

stack<Node*> neighborList;
priority_queue<Node*, vector<Node*>, customComp> pq;
unordered_map<string, int> g_nValues;

void build_neighborList(Node* node){
    Node* neighborNode;
    unsigned char** neighborGrid;
    unsigned char starTemp;
    bool flag = false;
    int star_row, star_col;
    for(int i=0;i<k;i++){
        for(int j=0;j<k;j++){
            if(node->grid[i][j]==0){
                star_row = i;
                star_col = j;
                flag = true;
                break;
            }
        }
        if(flag) break;
    }


    if(star_row==k-1){
        neighborNode = new Node;
        neighborNode->move = 0;
        neighborGrid = copy(node->grid);
        starTemp = neighborGrid[star_row][star_col];
        neighborGrid[star_row][star_col] = neighborGrid[star_row-1][star_col];
        neighborGrid[star_row-1][star_col] = starTemp;
        neighborNode->grid = neighborGrid;
        //neighborNode->parent = node;
        neighborList.push(neighborNode);
    }
    else if(star_row==0){
        neighborNode = new Node;
        neighborNode->move = 1;
        neighborGrid = copy(node->grid);
        starTemp = neighborGrid[star_row][star_col];
        neighborGrid[star_row][star_col] = neighborGrid[star_row+1][star_col];
        neighborGrid[star_row+1][star_col] = starTemp;
        neighborNode->grid = neighborGrid;
        //neighborNode->parent = node;
        neighborList.push(neighborNode);
    }
    else{
        neighborNode = new Node;
        neighborNode->move = 0;
        neighborGrid = copy(node->grid);
        starTemp = neighborGrid[star_row][star_col];
        neighborGrid[star_row][star_col] = neighborGrid[star_row-1][star_col];
        neighborGrid[star_row-1][star_col] = starTemp;
        neighborNode->grid = neighborGrid;
        //neighborNode->parent = node;
        neighborList.push(neighborNode);

        neighborNode = new Node;
        neighborNode->move = 1;
        neighborGrid = copy(node->grid);
        starTemp = neighborGrid[star_row][star_col];
        neighborGrid[star_row][star_col] = neighborGrid[star_row+1][star_col];
        neighborGrid[star_row+1][star_col] = starTemp;
        neighborNode->grid = neighborGrid;
        //neighborNode->parent = node;
        neighborList.push(neighborNode);
    }

    if(star_col==k-1){
        neighborNode = new Node;
        neighborNode->move = 2;
        neighborGrid = copy(node->grid);
        starTemp = neighborGrid[star_row][star_col];
        neighborGrid[star_row][star_col] = neighborGrid[star_row][star_col-1];
        neighborGrid[star_row][star_col-1] = starTemp;
        neighborNode->grid = neighborGrid;
        //neighborNode->parent = node;
        neighborList.push(neighborNode);
    }
    else if(star_col==0){
        neighborNode = new Node;
        neighborNode->move = 3;
        neighborGrid = copy(node->grid);
        starTemp = neighborGrid[star_row][star_col];
        neighborGrid[star_row][star_col] = neighborGrid[star_row][star_col+1];
        neighborGrid[star_row][star_col+1] = starTemp;
        neighborNode->grid = neighborGrid;
        //neighborNode->parent = node;
        neighborList.push(neighborNode);
    }
    else{
        neighborNode = new Node;
        neighborNode->move = 2;
        neighborGrid = copy(node->grid);
        starTemp = neighborGrid[star_row][star_col];
        neighborGrid[star_row][star_col] = neighborGrid[star_row][star_col-1];
        neighborGrid[star_row][star_col-1] = starTemp;
        neighborNode->grid = neighborGrid;
        //neighborNode->parent = node;
        neighborList.push(neighborNode);

        neighborNode = new Node;
        neighborNode->move = 3;
        neighborGrid = copy(node->grid);
        starTemp = neighborGrid[star_row][star_col];
        neighborGrid[star_row][star_col] = neighborGrid[star_row][star_col+1];
        neighborGrid[star_row][star_col+1] = starTemp;
        neighborNode->grid = neighborGrid;
        //neighborNode->parent = node;
        neighborList.push(neighborNode);
    }

}


bool isSolvable(unsigned char** gr){
    int invCnt = 0;
    int star_row, star_col;
    for(int i=0;i<k;i++){
        for(int j=0;j<k;j++){
            if(gr[i][j]==0){
                star_row = i;
                star_col = j;
            }
        }
    }
    int star_row_from_bottom = k-star_row;
    //cout<<"in solvable:"<<endl;
    //print(gr);

    /*for(int i=0;i<k;i++){
        for(int j=0;j<k;j++){
            if(gr[i][j]==0) continue;
            for(int p=i;p<k;p++){
                for(int q=j;q<k;q++){
                    if(gr[i][j]>gr[p][q] && gr[p][q]!=0) invCnt++;
                }
            }
            cout<<int(gr[i][j])<<":"<<invCnt<<endl;
        }
    }*/

    int row1, row2, col1, col2;
    for(int i=0; i<k*k; i++){
        for(int j=i+1; j<k*k; j++){
            row1 = i/k;
            col1 = i%k;
            row2 = j/k;
            col2 = j%k;
            if(gr[row1][col1]!=0 && gr[row2][col2]!=0 && gr[row1][col1]>gr[row2][col2]) invCnt++;
        }
    }

    //cout<<invCnt<<" "<<star_row_from_bottom<<endl;
    if(k%2==1){
        if(invCnt%2==0) return true;
        else return false;
    }
    else{
        if((invCnt+star_row_from_bottom)%2==1) return true;
        else return false;
    }
}



void A_star_Search(unsigned char** g, int h_choice){

    ///Clearing Things
    Node* t;
    while(!pq.empty()){
        t = pq.top();

        destruct(t->grid);
        delete t;
        pq.pop();
    }
    /*while(!neighborList.empty()){
        t = neighborList.top();
        neighborList.pop();
        destruct(t->grid);
        delete t;
    }*/
    //g_nValues.clear();
    g_nValues.erase(g_nValues.begin(), g_nValues.end());


    bool solvable = isSolvable(g); //solvable kina- er code likhte hobe
    int exploredCount = 0;
    int expandedCount = 0;
    int moveCount = 0;
    //cout<<solvable<<endl;

    if(!solvable){
        cout << "No solution" << endl;
        return;
    }
    else{

        Node* presentNode;
        Node* neighborNode;
        string map_idx = mapIdxMaker(g);
        g_nValues[map_idx] = 0;

        Node* initialNode = new Node;

        if(h_choice==1){
            initialNode->f_n = hammingDistance(g);
        }
        else if(h_choice==2){
            initialNode->f_n = manhattanDistance(g);
        }

        initialNode->g_n = 0;
        initialNode->parent = NULL;
        initialNode->grid = g;

        pq.push(initialNode);

        while(!pq.empty()){
            while(1){
                presentNode = pq.top();
                pq.pop();
                string tempI = mapIdxMaker(presentNode->grid);
                if(g_nValues[tempI] == presentNode->g_n){
                    break;
                }
                else{
                    destruct(presentNode->grid);
                    delete presentNode;
                }
            }
            if(moveCount<presentNode->g_n){
                moveCount = presentNode->g_n;
            }
            expandedCount++;

            bool targetAchieved = true;
            for(int i=0; i<k; i++){
                for(int j=0; j<k; j++){
                    if(presentNode->grid[i][j]!=targetGrid[i][j]){
                        targetAchieved = false;
                        break;
                    }
                }
                if(!targetAchieved) break;
            }

            if(targetAchieved==true){
                cout << "Solution found" << endl;
                stack<Node*> path;
                vector<string> moves;
                while(presentNode!=NULL){
                    path.push(presentNode);
                    presentNode = presentNode->parent;
                }
                while(!path.empty()){
                    Node* temp = path.top();
                    if(temp->parent!=NULL){
                        if(temp->move==0){
                            moves.push_back("UP");
                        }
                        else if(temp->move==1){
                            moves.push_back("DOWN");
                        }
                        else if(temp->move==2){
                            moves.push_back("LEFT");
                        }
                        else if(temp->move==3){
                            moves.push_back("RIGHT");
                        }
                    }
                    path.pop();

                }
                cout<<"Cost to reach the target state: "<<moveCount<<endl;
                cout<<"The Steps: ";
                for(int i=0; i<moves.size()-1; i++){
                    cout<<moves[i]<<"--";
                }
                cout<<moves[moves.size()-1]<<endl;
                cout<<"Number of nodes explored: "<<exploredCount<<endl;
                cout<<"Number of nodes expanded: "<<expandedCount<<endl;

                return;
            }




            build_neighborList(presentNode);

            /*while(!neighborList.empty()){
                Node* x = neighborList.top();
                neighborList.pop();
                print(x->grid);
            }*/


            while(!neighborList.empty()){
                neighborNode = neighborList.top();
                neighborList.pop();
                int neigh_gn = presentNode->g_n + 1;
                string neigh_idx = mapIdxMaker(neighborNode->grid);

                if((g_nValues.find(neigh_idx) !=  g_nValues.end() && g_nValues[neigh_idx]>neigh_gn) || (g_nValues.find(neigh_idx) ==  g_nValues.end())){

                    neighborNode->parent = presentNode;
                    neighborNode->g_n = neigh_gn;

                    if(h_choice==1){
                        neighborNode->f_n = neighborNode->g_n + hammingDistance(neighborNode->grid);
                    }
                    else if(h_choice==2){
                        neighborNode->f_n = neighborNode->g_n + manhattanDistance(neighborNode->grid);
                    }
                    pq.push(neighborNode);
                    if(g_nValues.find(neigh_idx) ==  g_nValues.end()){
                        exploredCount++;
                        //cout<<"hi"<<endl;
                    }
                    g_nValues[neigh_idx] = neighborNode->g_n;
                }
                else{
                    destruct(neighborNode->grid);
                    delete neighborNode;
                }
            }
        }

    }

}




int main(){
    //file open code
    int in_k;
    cin>>in_k;
    k = in_k;
    unsigned char** initialGrid = new unsigned char*[in_k];
    for(int i=0;i<in_k;i++){
        initialGrid[i] = new unsigned char[in_k];
        for(int j=0;j<in_k;j++){
            string a;
            cin>>a;
            if(a == "*"){
                initialGrid[i][j] = 0;
            }
            else{
                // stringstream geek(a);
                // geek>>initialGrid[i][j];
                initialGrid[i][j] = stoi(a);
            }
        }
    }
    print(initialGrid);
    unsigned char cnt = 1;
    targetGrid = new unsigned char*[in_k];
    for(int i=0; i<in_k; i++){
        targetGrid[i] = new unsigned char[in_k];
    }
    for(int i=0; i<in_k; i++){
        for(int j=0; j<in_k; j++){
            targetGrid[i][j] = cnt;
            cnt++;
        }
    }
    targetGrid[in_k-1][in_k-1] = 0;
    //print(targetGrid);
    cout<<"Using Hamming Distance:: "<<endl;
    A_star_Search(initialGrid, 1);
    cout<<endl;
    cout<<"================================================"<<endl;
    cout<<"Using Manhattan Distance:: "<<endl;
    A_star_Search(initialGrid, 2);
    cout<<endl;

}

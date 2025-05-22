class Solution {
    public int findCircleNum(int[][] isConnected) {
        int[] verticesMarcados = new int[isConnected.length];  // Array de visitação
        int numeroDeProvincias = 0;

        // Percorrer todas as cidades
        for (int i = 0; i < isConnected.length; i++) {
            if (verticesMarcados[i] == 0) {  // Se a cidade não foi visitada
                numeroDeProvincias++;  // Encontramos uma nova província
                buscaProfundidade(isConnected, i, verticesMarcados);  // Realiza a busca em profundidade
            }
        }

        return numeroDeProvincias;  // Retorna o total de províncias
    }

    private void buscaProfundidade(int[][] grafo, int indice, int[] verticesMarcados) {
        verticesMarcados[indice] = 1;  // Marca o vértice como visitado

        // Percorre os vizinhos do vértice
        for (int i = 0; i < grafo[indice].length; i++) {
            if (grafo[indice][i] == 1 && verticesMarcados[i] == 0) {
                buscaProfundidade(grafo, i, verticesMarcados);  // Chama recursivamente a busca
            }
        }
    }
}


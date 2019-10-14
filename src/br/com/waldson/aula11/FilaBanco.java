package br.com.waldson.aula11;
import java.util.Arrays;

public class FilaBanco {
    private Pessoa[] pessoas;
    private int size; //quantos elementos tem
    private int capacity; //quantos elementos cabem

    public FilaBanco() {
        this(10);
    }

    public FilaBanco(int capacity) {
        pessoas = new Pessoa[capacity];
        this.size = 0;
        this.capacity = capacity;
    }

    public void insert(String nome, int idade) {
        insert(new Pessoa(nome, idade));
    }

    public void insert(Pessoa pessoa) {
        ensureCapacity();
        pessoas[getSize()] = pessoa;
        heapifyUp(getSize());
        size++;
    }

    private void heapifyUp(int index) {
        int parentIndex = getParentIndex(index);
        if (parentIndex < 0) {
            return;
        }

        Pessoa pai    = pessoas[parentIndex];
        Pessoa pessoa = pessoas[index];

        if (pessoa.getIdade() > pai.getIdade()) {
            pessoas[index]   = pai;
            pessoas[parentIndex] = pessoa;
            heapifyUp(parentIndex);
        }
    }

    public int getParentIndex(int index) {
        return (int) Math.floor((index - 1) / 2);
    }

    private void ensureCapacity() {
        if (size == capacity) {
            pessoas = Arrays.copyOf(pessoas, capacity * 2);
            capacity = capacity * 2;
        }
    }

    public int getSize() {
        return size;
    }

    public Pessoa peek() {
        if (getSize() == 0) {
            return null;
        }

        return pessoas[0];
    }

    public void remove() {
        pessoas[0] = pessoas[getSize() - 1];
        pessoas[getSize() - 1] = null;
        size--;
        heapifyDown(0);
    }

    private void heapifyDown(int index) {
        int leftChild = index * 2 + 1;
        int rightChild = index * 2 + 2;

        int childIndex = -1;
        if (leftChild < getSize()) {
            childIndex = leftChild;
        }

        if (childIndex == -1) {
            return;
        }

        if (rightChild < getSize()) {
            if (pessoas[rightChild].getIdade() > pessoas[leftChild].getIdade()) {
                childIndex = rightChild;
            }
        }

        if (pessoas[index].getIdade() < pessoas[childIndex].getIdade()) {
            Pessoa tmp          = pessoas[index];
            pessoas[index]      = pessoas[childIndex];
            pessoas[childIndex] = tmp;
            heapifyDown(childIndex);
        }
    }

    public void maxheap (int index) {
	if (pessoas[index*2 +2] == null) {
	    if (pessoas[index*2 +1].getIdade() > pessoas[index].getIdade()) {
		Pessoa tmp = pessoas[index];
		pessoas[index] = pessoas[index*2 +1];
		pessoas[index*2 +1] = tmp;
	    }
	} else {
	    int max = index;
	    if (pessoas[index*2 +1].getIdade() > pessoas[max].getIdade()) max = index*2 +1;
	    if (pessoas[index*2 +2].getIdade() > pessoas[max].getIdade()) max = index*2 +2;
	    if (max != index) {
		Pessoa tmp = pessoas[index];
		pessoas[index] = pessoas[max];
		pessoas[max] = tmp;
	    }
	}
    }

    public void heapsort () {
	int j = size;
	boolean check = true;
	while (j > 0) {
	    Pessoa[] old = pessoas;
	    while(check) {
		check = false;
		for (int i = 0; i < j/2; i++) maxheap(i);
		if (!Arrays.equals(old, pessoas)) check = true;
	    }

	    if (!Arrays.equals(old, pessoas)) {
		Pessoa tmp = pessoas[j-1];
		pessoas[j-1] = pessoas[0];
		pessoas[0] = tmp;
	    }
	    j--;
	}
    }
}

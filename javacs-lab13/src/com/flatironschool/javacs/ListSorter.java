/**
 * 
 */
package com.flatironschool.javacs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Provides sorting algorithms.
 *
 */
public class ListSorter<T> {

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void insertionSort(List<T> list, Comparator<T> comparator) {
	
		for (int i=1; i < list.size(); i++) {
			T elt_i = list.get(i);
			int j = i;
			while (j > 0) {
				T elt_j = list.get(j-1);
				if (comparator.compare(elt_i, elt_j) >= 0) {
					break;
				}
				list.set(j, elt_j);
				j--;
			}
			list.set(j, elt_i);
		}
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void mergeSortInPlace(List<T> list, Comparator<T> comparator) {
		List<T> sorted = mergeSort(list, comparator);
		list.clear();
		list.addAll(sorted);
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * Returns a list that might be new.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public List<T> mergeSort(List<T> list, Comparator<T> comparator) {
        // Base case
        if (list.size() < 2)
        	return list;
        // Split the list into halves recursively and sort them.
        List<T> first = new LinkedList<>(list.subList(0, list.size()/2));
        Collections.sort(first, comparator);
        List<T> second = new LinkedList<>(list.subList(list.size()/2, list.size()));
        Collections.sort(second, comparator);
        // Merge the two sorted halves back together.
        List<T> merged = new LinkedList<>();
        int first_ptr = 0, second_ptr = 0;
        while (first_ptr < first.size() && second_ptr < second.size())
        {
        	int result = comparator.compare(first.get(first_ptr), second.get(second_ptr));
        	if (result < 0 )
        	{
        		merged.add(first.get(first_ptr));
        		first_ptr++;
        	}
        	else
        	{
        		merged.add(second.get(second_ptr));
        		second_ptr++;
        	}
        }
        if (first_ptr < first.size())
        	while (first_ptr < first.size())
        	{
        		merged.add(first.get(first_ptr));
        		first_ptr++;
        	}
        if (second_ptr < second.size())
        	while (second_ptr < second.size())
        	{
        		merged.add(second.get(second_ptr));
        		second_ptr++;
        	}
        return merged;
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void heapSort(List<T> list, Comparator<T> comparator) {
        PriorityQueue<T> temp = new PriorityQueue<>();
        // Add all elements of the collection to a PriorityQueue using offer
        for (T element: list)
        	temp.offer(element);
        // Remove the elements from the queue using poll and add them to a List
        list.clear();
        while (temp.size() > 0)
        	list.add(temp.poll());
	}

	
	/**
	 * Returns the largest `k` elements in `list` in ascending order.
	 * 
	 * @param k
	 * @param list
	 * @param comparator
	 * @return 
	 * @return
	 */
	public List<T> topK(int k, List<T> list, Comparator<T> comparator) {
        PriorityQueue<T> temp = new PriorityQueue<>();
        for (T element: list)
        {
        	// 1: Add it if queue not full
        	if (temp.size() < k)
        		temp.offer(element);
        	// If the queue is full, compare element to current smallest element;
        	// if it's larger, kick that element out and add this element.
        	else if (comparator.compare(element, temp.peek()) > 0)
        	{
        		temp.poll();
        		temp.offer(element);
        	}
        }
        // Make a new list because this isn't an in-place operation
        List<T> top = new LinkedList<>();
        while (temp.size() > 0)
        	top.add(temp.poll());
        return top;
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		
		Comparator<Integer> comparator = new Comparator<Integer>() {
			@Override
			public int compare(Integer n, Integer m) {
				return n.compareTo(m);
			}
		};
		
		ListSorter<Integer> sorter = new ListSorter<Integer>();
		sorter.insertionSort(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.mergeSortInPlace(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.heapSort(list, comparator);
		System.out.println(list);
	
		list = new ArrayList<Integer>(Arrays.asList(6, 3, 5, 8, 1, 4, 2, 7));
		List<Integer> queue = sorter.topK(4, list, comparator);
		System.out.println(queue);
	}
}

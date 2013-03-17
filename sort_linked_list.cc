// Author: Cong Li
// Date: 3/17/2013
// Problem description:
//
// Sort a linked list.
//
// =============================
// The following code is a merge sort algorithm of linked list.

#include <iostream>
#include <vector>
using namespace std;

class ListNode
{
public:
	ListNode(int val)
	{
		value = val;
		next = NULL;
	}
	int value;
	ListNode *next;
};

void merge(ListNode *first, int length1, ListNode *second, int length2)
{
	vector<int> temp;
	ListNode *runner = first;
	int count1 = 0;
	int count2 = 0;
	while(count1 < length1 || count2 < length2)
	{
		if(count1 < length1 && count2 < length2)
		{
			if(first->value < second->value)
			{
				temp.push_back(first->value);
				first = first->next;
				++count1;
			}
			else
			{
				temp.push_back(second->value);
				second = second->next;
				++count2;
			}
		}
		else if(count1 < length1)
		{
			temp.push_back(first->value);
			first = first->next;
			++count1;
		}
		else
		{
			temp.push_back(second->value);
			second = second->next;
			++count2;
		}
	}
	for(size_t i = 0; i < temp.size(); ++i)
	{
		runner->value = temp[i];
		runner = runner->next;
	}
}

void helper(ListNode *head, int length)
{
	if(length < 2) return;
	int mid_length = length/2;
	ListNode *runner = head;
	int count = 0;
	while(count < mid_length)
	{
		runner = runner->next;
		++count;
	}
	helper(head, mid_length);
	helper(runner, length-mid_length);
	merge(head, mid_length, runner, length-mid_length);
}

ListNode *sort_linked_list(ListNode *head)
{
	if(head == NULL || head->next == NULL) return head;
	int length = 0;
	ListNode *runner = head;
	while(runner != NULL)
	{
		runner = runner->next;
		++length;
	}

	helper(head, length);
	return head;
}

void print_list(ListNode *head)
{
	while(head != NULL)
	{
		cout << head->value << " ";
		head = head->next;
	}
	cout << endl;
}

int main()
{
	int array[] = {5, 3, 1, 2, 4};
	ListNode *head = new ListNode(array[0]);
	ListNode *runner = head;
	for(int i = 1; i < 5; ++i)
	{
		runner->next = new ListNode(array[i]);
		runner = runner->next;
	}
	print_list(head);
	sort_linked_list(head);
	print_list(head);
}

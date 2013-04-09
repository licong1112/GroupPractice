// Author: Cong Li
// Practiced on 4/8/2013
//
// Given an arithmatic expression which contains only numbers 0-9 and operands + - * / ( ),
// evaluate the value of the expression. Assume the given expression is valid (by valid
// we mean the expression can be evaluated without any logical or mathematical issue).
//
// ======================================================
// Reverse Polish Notation (RPN) is a solution to this problem.
// For details of RPN, check here: http://en.wikipedia.org/wiki/Reverse_Polish_notation
// 
// 1. Generate the RPN of a given expression. Note that the RPN of a given
//    expression may not be unique.
//    A simple algorithm called "Shunting-yard algorithm" can do the job. For
//    the details, check here: http://en.wikipedia.org/wiki/Shunting-yard_algorithm
//
// 2. Based on the RPN, evaluate the expression value.
//
// Be careful, the RPN can reverse the order of calculation!
// For example, "6*2/4" will become "624/*". Then when evaluating the RPN,
// we first evaluate 2/4, which will be 0 if we do int division. This will
// give a wrong answer. So either convert to double, or reverse the order
// of evaluation of consecutive operands which have the same precedence.

import java.util.LinkedList;
import java.util.Stack;

public class EvaluateExpression {

	public static void main(String[] args) {
		EvaluateExpression test = new EvaluateExpression();
		
		String rpn = test.ToRPN("3+4*3/(1-5)+2");
		System.out.println(rpn);
		test.EvalRPN(rpn);
	}

	public String ToRPN(String str)
	{
		LinkedList<Character> queue = new LinkedList<Character>();
		Stack<Character> stack = new Stack<Character>();
		
		for(int i = 0; i < str.length(); ++i)
		{
			char curr_char = str.charAt(i);
			if(curr_char >= '0' && curr_char <= '9')
				queue.addLast(curr_char);
			else
			{
				if(curr_char == '(')
					stack.push(curr_char);
				else if(curr_char == ')')
				{
					while(stack.peek() != '(')
						queue.addLast(stack.pop());
					stack.pop();
				}
				else if(curr_char == '+' || curr_char == '-')
				{
					while(!stack.isEmpty() && (stack.peek() == '*' || stack.peek() == '/'))
						queue.addLast(stack.pop());
					stack.push(curr_char);
				}
				else // '*' or '/'
					stack.push(curr_char);
			}
		}
		while(!stack.isEmpty())
			queue.addLast(stack.pop());
		
		StringBuilder sb = new StringBuilder();
		while(!queue.isEmpty())
			sb.append(queue.pollFirst());
		return sb.toString();
	}
	
	public void EvalRPN(String str)
	{
		Stack<Double> stack = new Stack<Double>();
		double a = 0; 
		double b = 0;
		for(int i = 0; i < str.length(); ++i)
		{
			char curr_char = str.charAt(i);
			if(curr_char >= '0' && curr_char <= '9')
				stack.push((double)(curr_char - '0'));
			else
			{
				a = stack.pop();
				b = stack.pop();
				switch(curr_char)
				{
				case '+':
					stack.push(b+a);
					break;
				case '-':
					stack.push(b-a);
					break;
				case '*':
					stack.push(b*a);
					break;
				case '/':
					stack.push(b/a);
					break;
				}
			}
		}
		System.out.println(stack.pop().intValue());
	}
}

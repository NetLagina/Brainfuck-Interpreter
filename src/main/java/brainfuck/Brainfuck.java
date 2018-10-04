package brainfuck;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import exception.IllegalOperatorException;
import exception.PointerOutOfBoundsException;

public class Brainfuck {
	
	/*
	 * There is unlimited memory in Brainfuck but often realizations have 30K bytes.
	 * "Minimum 30K bytes" says Wikipedia.
	 */
	private final static int MEMORY_SIZE = 60_000;

	private int[] memory = new int[MEMORY_SIZE];
	
	/*
	 * Wiki says that "data pointer must be initialized to point to the leftmost byte of the array"
	 * but some programs use negative memory-array positions.
	 */
	private int pointer = MEMORY_SIZE / 2;

	private String code;
	private int commandPointer = 0;

	private String inputString;
	private int inputPointer = 0;

	private LoopManager loopManager;

	private StringBuilder outputString = new StringBuilder();

	public Brainfuck(String code) {
		this.code = code;
		parseLoops(code);
	}

	private void parseLoops(String code) {
		List<Integer> loopBeginPointers = new ArrayList<>();
		List<Integer> loopEndPointers = new ArrayList<>();

		for (int i = 0; i < code.length(); i++) {
			if (code.charAt(i) == '[') {
				loopBeginPointers.add(i);
			}
		}

		for (int loopBeginPointer : loopBeginPointers) {
			int inLoops = 0;
			for (int i = loopBeginPointer + 1; i < code.length(); i++) {
				if (code.charAt(i) == '[') {
					++inLoops;
				} else if (code.charAt(i) == ']') {
					if (inLoops > 0) {
						--inLoops;
					} else {
						loopEndPointers.add(i);
						break;
					}
				}
			}
		}

		loopManager = new LoopManager(loopBeginPointers, loopEndPointers);
	}

	public String process(String input) throws PointerOutOfBoundsException, IOException, IllegalOperatorException {
		this.inputString = input;

		while (commandPointer < code.length()) {
			char command = code.charAt(commandPointer);
			switch (command) {
			case '>':
				incPointer();
				break;
			case '<':
				decPointer();
				break;
			case '+':
				inc();
				break;
			case '-':
				dec();
				break;
			case '.':
				output();
				break;
			case ',':
				input();
				break;
			case '[':
				jmp();
				break;
			case ']':
				jmpbck();
				break;
			case ' ':
			case '\n':
			case '\r':
			default:
				break;
			}

			++commandPointer;
		}
		
		return outputString.toString();
	}

	private void incPointer() throws PointerOutOfBoundsException {
		if ((pointer + 1) < memory.length) {
			++pointer;
		} else {
			throw new PointerOutOfBoundsException(String.valueOf(++pointer));
		}
	}

	private void decPointer() throws PointerOutOfBoundsException {
		if ((pointer - 1) > -1) {
			--pointer;
		} else {
			throw new PointerOutOfBoundsException(String.valueOf(--pointer));
		}
	}

	private void inc() {
		if ((memory[pointer] + 1) < 255) {
			++memory[pointer];
		} else {
			memory[pointer] -= 255; // + 1 - 256
		}
	}

	private void dec() {
		if ((memory[pointer] - 1) >= 0) {
			--memory[pointer];
		} else {
			memory[pointer] += 255; // -1 + 256
		}
	}

	private void input() throws IOException {
		if (inputPointer < inputString.length()) {
			memory[pointer] = inputString.charAt(inputPointer);
			++inputPointer;
		} else {
			throw new IOException(String.valueOf(inputPointer));
		}
	}
	
	private void output() {
		outputString.append((char) memory[pointer]);
	}

	private void jmp() {
		if (memory[pointer] == 0) {
			commandPointer = loopManager.getLoopEndPointer(commandPointer);
		}
	}

	private void jmpbck() {
		if (memory[pointer] != 0) {
			commandPointer = loopManager.getLoopBeginPointer(commandPointer);
		}
	}

	public class LoopManager {

		private final List<Integer> loopBeginPointers;
		private final List<Integer> loopEndPointers;

		public LoopManager(List<Integer> loopBeginPointers, List<Integer> loopEndPointers) {
			this.loopBeginPointers = loopBeginPointers;
			this.loopEndPointers = loopEndPointers;
		}

		public int getLoopBeginPointer(int loopEndPointer) {
			return loopBeginPointers.get(loopEndPointers.indexOf(loopEndPointer));
		}

		public int getLoopEndPointer(int loopBeginPointer) {
			return loopEndPointers.get(loopBeginPointers.indexOf(loopBeginPointer));
		}

	}

}

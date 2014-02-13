package sGen.teachu.forPlay;

import android.provider.UserDictionary.Words;
import android.view.inputmethod.CorrectionInfo;


public class Compareword {
	// 정답 단어 / 입력된 단어
	private String correctword, inputword;
	// 음절 유니코드 배열
	private int[] syllable_word_array, syllable_inputword_array;
	// 음소 유니코드 배열
	private int[] phoneme_word_array, phoneme_inputword_array;

	public double[] analysis_word_array;

	public Compareword(String word, String inputword) {
		super();
		this.correctword = eraseSpace(word);
		this.inputword = eraseSpace(inputword);

		// 정답단어 분석
		syllable_word_array = getSyllablewordarray(correctword);
		phoneme_word_array = getPhonemewordarray(syllable_word_array);

		// 입력된 단어 분석
		syllable_inputword_array = getSyllablewordarray(inputword);
		phoneme_inputword_array = getPhonemewordarray(syllable_inputword_array);

		analysis_word_array = compare(phoneme_word_array,
				phoneme_inputword_array);

		System.out.println(phoneme_word_array);
	}

	private String eraseSpace(String word) {
		return word.replace(" ", "");
	}

	// 음절의 유니코드 배열을 얻는 함수
	int[] getSyllablewordarray(String word) {
		int[] syllable_array = new int[word.length()];
		for (int i = 0; i < word.length(); i++) {
			syllable_array[i] = word.codePointAt(i);
			// System.out.println("syllable_array[i] : "+syllable_array[i]);
		}
		return syllable_array;
	}

	// 음소의 유니코드 배열을 얻는 함수
	int[] getPhonemewordarray(int[] syllable_array) {
		int[] phoneme_array = new int[syllable_array.length * 3];
		int i = 0;
		// 음절의 길이 만큼 반복
		for (int j = 0; j < syllable_array.length; j++) {
			// 인덱스 0~2까지, 3~5까지 반복
			for (; i < (j + 1) * 3; i++) {
				if (i % 3 == 0) {
					// System.out.println("i : " + i);
					phoneme_array[i] = getInitialcode(syllable_array[j]);
				} else if (i % 3 == 1) {
					// System.out.println("i : " + i);
					phoneme_array[i] = getMedialcode(syllable_array[j]);
				} else if (i % 3 == 2) {
					// System.out.println("i : " + i);
					phoneme_array[i] = getFinalcode(syllable_array[j]);
				}
			}
		}

		return phoneme_array;
	}

	int isSimilarVowel(int phoneme_word, int phoneme_inputword) {
		// 행열

		// 이 배열에 발음이 같은 자음 리스트 적으면 됨!
		// http://thdwlgnsdl10.blog.me/40063114545 이 사이트에 유니코드값 나와있음
		int[][] similarlist = { { 0, 2, -1, -1, -1, -1, -1, -1, -1, -1 },
				{ 1, 3, 4, 5, 6, 7, 10, 11, 14, 15 },
				{ 8, 9, 12, -1, -1, -1, -1, -1, -1, -1 },
				{ 13, 16, 17, 18, 19, 20, -1, -1, -1, -1 } };
		// ㄱ,ㅋ / ㄴ,ㄹ / ㄷ,ㅌ / ㅁ,ㅂ,ㅍ / ㅅ,ㅈ,ㅊ ㅇ,ㅎ

		int phoneme_word_row = 0;
		int phoneme_inputword_row = 0;

		// 2차원 배열을 돌면서 2차원 배열 내에서 입력된 단어의 코드와 일치하는 코드의 행을 찾는다
		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 10; col++) {
				if (phoneme_word == similarlist[row][col])
					phoneme_word_row = row;
				if (phoneme_inputword == similarlist[row][col])
					phoneme_inputword_row = row;
			}
		}
		if (phoneme_word_row == phoneme_inputword_row)
			return phoneme_word_row;
		else
			return -1;
	}

	int isSimilarConsonant(int phoneme_word, int phoneme_inputword) {
		// 행열

		// 이 배열에 발음이 같은 자음 리스트 적으면 됨!
		// http://thdwlgnsdl10.blog.me/40063114545 이 사이트에 유니코드값 나와있음
		int[][] similarlist = { { 15, 0, -1 }, { 2, 5, -1 }, { 3, 16, -1 },
				{ 6, 7, 17 }, { 12, 9, 14 }, { -1, 18, 11 } };
		// ㄱ,ㅋ / ㄴ,ㄹ / ㄷ,ㅌ / ㅁ,ㅂ,ㅍ / ㅅ,ㅈ,ㅊ / ㅇ,ㅎ

		int phoneme_word_row = 0;
		int phoneme_inputword_row = 0;

		// 2차원 배열을 돌면서 2차원 배열 내에서 입력된 단어의 코드와 일치하는 코드의 행을 찾는다
		for (int row = 0; row < 6; row++) {
			for (int col = 0; col < 3; col++) {
				if (phoneme_word == similarlist[row][col])
					phoneme_word_row = row;
				if (phoneme_inputword == similarlist[row][col])
					phoneme_inputword_row = row;
			}
		}
		// System.out.println(phoneme_word_row+phoneme_inputword_row);
		if (phoneme_word_row == phoneme_inputword_row)
			return phoneme_word_row;
		else
			return -1;
	}

	// 비교 결과 배열을 리턴하는 함수
	double[] compare(int[] phoneme_word_array, int[] phoneme_inputword_array) {
		// int[] analysis_word_array = new int[phoneme_word_array.length];
		// 구현
		analysis_word_array = new double[phoneme_word_array.length];
		// 비교하기
		// 음소 배열 비교 후, 다른 경우에는 isSimilar로 넘어감. 초성: 0.3 중성: 0.4 종성: 0.3
		
		System.out.println(correctword);
		System.out.println(inputword);

		int i = 0;
		int n = 0, k = 0;
		if (syllable_word_array.length == syllable_inputword_array.length) {
			for (int j = 0; j < syllable_word_array.length; j++) {
				// 인덱스 0~2까지, 3~5까지 반복
				for (; i < (j + 1) * 3; i++) {
					if (i % 3 == 0) {
						if (phoneme_word_array[i] == (phoneme_inputword_array[i]))
							analysis_word_array[i] = 0.3;
						else {
							if (isSimilarConsonant(phoneme_word_array[i],
									phoneme_inputword_array[i]) != -1)
								analysis_word_array[i] = 0.2;
							else
								analysis_word_array[i] = 0;
						}
					} else if (i % 3 == 1) {
						// System.out.println("i : " + i);
						if (phoneme_word_array[i] == (phoneme_inputword_array[i]))
							analysis_word_array[i] = 0.4;
						else {
							if (isSimilarVowel(phoneme_word_array[i],
									phoneme_inputword_array[i]) != -1)
								analysis_word_array[i] = 0.2;
							else
								analysis_word_array[i] = 0;
						}

					} else if (i % 3 == 2) {
						// System.out.println("i : " + i);
						if (phoneme_word_array[i] == 0) {
							analysis_word_array[i - 2] = analysis_word_array[i - 2]
									* (4 / 3);
							analysis_word_array[i - 1] = analysis_word_array[i - 1] * 1.5;
							analysis_word_array[i] = 0;
						} else {
							if (phoneme_word_array[i] == (phoneme_inputword_array[i]))
								analysis_word_array[i] = 0.3;
							else {
								if (isSimilarConsonant(phoneme_word_array[i],
										phoneme_inputword_array[i]) != -1)
									analysis_word_array[i] = 0.2;
								else
									analysis_word_array[i] = 0;
							}
						}
					}
				}
			}
		} else if (syllable_word_array.length < syllable_inputword_array.length) {
			for (n = 0; n < phoneme_word_array.length; n++) {
				for (k = 0; k < phoneme_inputword_array.length; k++) {
					if (n % 3 == 0) {
						if (phoneme_word_array[n] == phoneme_inputword_array[k])
							analysis_word_array[n] = 0.25;
					} else if (n % 3 == 1) {
						if (phoneme_word_array[n] == phoneme_inputword_array[k])
							analysis_word_array[n] = 0.35;
					} else if (n % 3 == 2) {
						if (phoneme_word_array[n] == 0) {
							analysis_word_array[n - 2] *= (4 / 3);
							analysis_word_array[n - 1] *= (3 / 2);
							analysis_word_array[n] = 0;
						} else{
							if (phoneme_word_array[n] == phoneme_inputword_array[k])
								analysis_word_array[n] = 0.25;
							else
								analysis_word_array[n]=0;
						}
					}
				}
			}
		} else { // 정답 단어 음절 수 > 입력 단어 음절 수
			for (n = 0; n < phoneme_inputword_array.length; n++) {
				for (k = 0; k < phoneme_inputword_array.length; k++) {
					if (n % 3 == 0) {
						if (phoneme_word_array[n] == phoneme_inputword_array[k])
							analysis_word_array[n] = 0.25;
					} else if (n % 3 == 1) {
						if (phoneme_word_array[n] == phoneme_inputword_array[k])
							analysis_word_array[n] = 0.35;
					} else if (n % 3 == 2) {
						if (phoneme_word_array[n] == 0) {
							analysis_word_array[n - 2] *= (4 / 3);
							analysis_word_array[n - 1] *= (3 / 2);
							analysis_word_array[n] = 0;
						} else{
							if (phoneme_word_array[n] == phoneme_inputword_array[k])
								analysis_word_array[n] = 0.25;
							else
								analysis_word_array[n] = 0;
						}
						}
				}

			}
			for (i = n + 1; i < phoneme_word_array.length; i++)
				analysis_word_array[i] = 0;
		}
			for(int a=0;a<analysis_word_array.length;a++)
				System.out.println(analysis_word_array[a]);

		return analysis_word_array;
	}

	/*
	 * for (int i = 0; i < syllable_word_array.length; i++) { // 초성 if
	 * (phoneme_word_array[0 + i * 3] == (phoneme_inputword_array[0 + i * 3]))
	 * analysis_word_array[0 + i * 3] = 0.3; else { if
	 * (isSimilarConsonant(phoneme_word_array[0 + i * 3],
	 * phoneme_inputword_array[0 + i * 3]) != -1) analysis_word_array[0 + i * 3]
	 * = 0.2; else analysis_word_array[0 + i * 3] = 0.1; } // 중성 if
	 * (phoneme_word_array[1 + i * 3] == (phoneme_inputword_array[1 + i * 3]))
	 * analysis_word_array[1 + i * 3] = 0.4; else { if
	 * (isSimilarVowel(phoneme_word_array[1 + i * 3], phoneme_inputword_array[1
	 * + i * 3]) != -1) analysis_word_array[1 + i * 3] = 0.3; else
	 * analysis_word_array[1 + i * 3] = 0.2; } // 종성 if (phoneme_word_array[2 +
	 * i * 3] == (phoneme_inputword_array[2 + i * 3])) analysis_word_array[2 + i
	 * * 3] = 0.3; else { if (isSimilarConsonant(phoneme_word_array[2 + i * 3],
	 * phoneme_inputword_array[2 + i * 3]) != -1) analysis_word_array[2 + i * 3]
	 * = 0.2; else analysis_word_array[2 + i * 3] = 0.1; }
	 * 
	 * }
	 */

	// 초성 얻는 함수
	int getInitialcode(int code) {
		int initcode;
		initcode = ((code - 44032) / 28) / 21;
		// System.out.println("initcode : "+initcode);
		return initcode;
	}

	// 중성 얻는 함수
	int getMedialcode(int code) {
		int medialcode;
		medialcode = ((code - 44032) / 28) % 21;
		// System.out.println("medialcode : "+medialcode);
		return medialcode;
	}

	// 종성 얻는 함수
	int getFinalcode(int code) {
		int finalcode;
		finalcode = (code - 44032) % 28;
		// System.out.println("finalcode : "+finalcode);
		return finalcode;
	}

	public double getCorrectionrate(double[] analysis_word_array) {
		double point_sum = 0;
		double correct_ration;
		for (int i = 0; i < analysis_word_array.length; i++) {

			// System.out.println("analysis_word_array" +
			// analysis_word_array[i]);
			point_sum += analysis_word_array[i];
			// System.out.println("point_sum" + point_sum);
		}

		// System.out.println("길이" + syllable_word_array.length);

		correct_ration = (point_sum) / (syllable_word_array.length) * 100;

		System.out.println(correct_ration);
		return correct_ration;
	}
	/*
	 * public static void main(String[] args) { Compareword analy = new
	 * Compareword("사과","까까"); analy.compare(analy.phoneme_word_array,
	 * analy.phoneme_inputword_array); for (int i = 0; i
	 * <analy.analysis_word_array.length; i++) {
	 * 
	 * System.out.println("i [ " + i + "] : " + analy.analysis_word_array[i]);
	 * 
	 * System.out.println("analy.phoneme_word_array[ " + i + "] : " +
	 * analy.phoneme_word_array[i]);
	 * System.out.println("analy.phoneme_inputword_array [ " + i + "] : "
	 * +analy.phoneme_inputword_array[i]);
	 * 
	 * }
	 * 
	 * System.out.println(analy.getCorrectionrate(analy.analysis_word_array));
	 * 
	 * // System.out.println(analy.isSimilar(4, 7)); }
	 * 
	 * 
	 * }
	 */
}

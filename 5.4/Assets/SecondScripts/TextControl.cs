using UnityEngine;
using System.Collections;
using UnityEngine.UI;
public class TextControl : MonoBehaviour {
	public static int num=0;
	public static float timer=0.6f;
	Text text;
	public static string str;
	// Use this for initialization
	void Start () {
	 
		text=GameObject.Find("ShowText").GetComponent<Text>();
	    str = text.text;

	}
	
	// Update is called once per frame
	void Update () {
	
		if (timer > 0f) {
			timer -= Time.deltaTime;
			return ;
		}


		if (num > 5) {
			if (str == "Connection fail")
				reset ("not");
			num = 0;
		}
			
		string showtext = str;
		for (int i = 0; i < num; i++) {
		  
			showtext+=". ";

		}
		//print (showtext);
		text.text = showtext;
		timer = 0.6f;
		num++;
	}

	public  static void reset(string type){
		num=0;
		timer = 0.6f;
		if (type == "connect")
			str = "Connecting";
		else if (type == "not")
			str = "Not Connected";
		else if (type == "fail")
			str = "Connection fail";
		else if (type == "wait")
			str = "success and waiting";
	}

}

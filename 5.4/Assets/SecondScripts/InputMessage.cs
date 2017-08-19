using UnityEngine;
using System.Collections;
using UnityEngine.UI;
public class InputMessage : MonoBehaviour {
	private static  InputField ip;
	private static  InputField port;
	public static string s_ip, s_port;
	// Use this for initialization
	void Start () {
		ip = GameObject.Find ("IPInputField").GetComponent<InputField>();
		port = GameObject.Find ("PortInputField").GetComponent<InputField>();
	}
	
	// Update is called once per frame
	void Update () {
	
	}

	public static void getmessage(){
		s_ip = ip.text;
		s_port = port.text;
	}

}

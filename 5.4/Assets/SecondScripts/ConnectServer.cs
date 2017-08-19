using UnityEngine;
using System.Collections;
using System.Threading;
using System.Net.Sockets;
using System.Text;
using System.Collections.Generic;
using UnityEngine.SceneManagement;
using UnityEngine.UI;
public class ConnectServer : MonoBehaviour {
	public int player;
	private float timer = 5f;
	public float speed=6.0f;

	private string store_msg="";
	private bool gamestart = false;
	private Queue<string> queue = new Queue<string> ();
	public static ConnectServer _instance=null;
	public bool DontDestroyOnload = true;
	public  Socket client;

	public string instruction="";
	public string [] recive_instruction;


	public Thread threadReceive, threadHadle;
	// Use this for initialization
	void Awake(){
		if (_instance != null) {
			GameObject.Destroy (this.gameObject);
			return;
		}
		_instance = this;
		if (this.DontDestroyOnload)
			GameObject.DontDestroyOnLoad (this);
			
	}
	void Start () {
	
	}
	
	// Update is called once per frame
	void Update () {
		if (client == null||gamestart==false)
			return;
		//all client can do it .  send transform msg 
		string s1, s2, s3;
		if(Input.GetAxis("Horizontal")>0)
			s1="1";
		else if(Input.GetAxis("Horizontal")<0)
			s1="-1";
		else
			s1="0";

		if (Input.GetAxis ("Vertical") > 0)
			s2 = "1";
		else if (Input.GetAxis ("Vertical") < 0)
			s2 = "-1";
		else
			s2 = "0";

		if(Input.GetKeyDown(KeyCode.Space))
			s3="1";
		else
			s3="0";
		if (s1 == "0" && s2 == "0" && s3 == "0") {
			
		} else {
			instruction = player + " " + s1 + " " + s2 + " " + s3;
			byte[] instruction_buffer = System.Text.Encoding.UTF8.GetBytes (instruction);
			instruction = "";
			client.Send (instruction_buffer);
		}

		//only host computer can do it, include request speed ,  send gameover msg , send score msg...
		if (player != 1)
			return;
		timer -= Time.deltaTime;
		if (timer < 0) {
			string msg="request";
			byte[] send_buffer = System.Text.Encoding.UTF8.GetBytes (msg);
			client.Send (send_buffer);
			timer = 5f;
		}




	}

	public  bool Connect(string ip,string port){
	
		client = new Socket (AddressFamily.InterNetwork,SocketType.Stream,ProtocolType.Tcp);
		try{
			
			client.Connect(ip,int.Parse(port));

		}catch(System.Exception e){
		
			e.GetHashCode ();
			client.Close ();
			client = null;
			return false;

		}

		if(client!=null)
			threadstart();
		return true;
	}
		

	public void threadstart(){
		if (client == null)
			return;
		//print ("start");
		this.threadReceive=new Thread(ReceiveMsg);
		this.threadReceive.IsBackground=true;
		this.threadReceive.Start();

		this.threadHadle=new Thread(HandleMsg);
		this.threadHadle.IsBackground=true;
		this.threadHadle.Start();

	}

	private void ReceiveMsg(){
	//	print ("receive");
	
		while (true) {
		
			if (client == null) {
				
				threadReceive.Abort ();
				break;
			}

			Thread.Sleep (1);

			byte[] buffer = new byte[1024];
			client.Receive (buffer);
			string msg = Encoding.UTF8.GetString (buffer).Replace ("\0","");
			for (int l = 0; l < msg.Length; l++) {
				if (msg [l] == '*') {
					if (store_msg != "")
						queue.Enqueue (store_msg);
					store_msg = "";
				} else {
					store_msg += msg [l];
				}
			}

		}




	}

	private void HandleMsg(){
	//	print ("handle");
		while (true) {

			if (client == null) {
				threadReceive.Abort ();
				break;
			}

			Thread.Sleep (1);

			if (queue.Count > 0) {
				
				string msg = queue.Dequeue ();
				print (msg);

				switch (msg) {

				case("start"):
					gamestart = true;
					break;
				case("score"):
					UIManager.score++;
					break;
				default:

					switch (msg [0]) {
					case('s'):
						string s = msg.Substring (1);
						speed = float.Parse (s);
						print (speed);				
						break;

					case('p'):
						string p = msg.Substring (1);
						player = int.Parse (p);
						print (player);
						break;
					
					case('i'):
						recive_instruction = msg.Substring (1).Split(' ');
						break;

					default:
						
						break;

					}
					

					break;

				}




			}
			
		}
	
	}
		

	public bool game_start(){
		if (gamestart == false)
			return false;
		
			return true;
	}

}

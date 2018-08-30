package list;

class LockDList extends DList {
	 
	
	LockDList() {
		super();
	}//此函数可省略






	public void lockNode(DListNode node) {
		LockDListNode n;
		if (!(node instanceof LockDListNode)){
			return;
		}
		n = (LockDListNode)node;
		n.Locked = 1;
	}

    protected DListNode newNode(Object item, DListNode prev, DListNode next) {
		return new LockDListNode(item, prev, next);
		}
  
  
  
  
  public void remove(DListNode node) {

		if (!(node instanceof LockDListNode)){
			return;
		}
		LockDListNode n = (LockDListNode)node;

		if (n.Locked == 0){
			super.remove(n);
		}
		if (n.Locked == 1){
			return;
		}

  }

}

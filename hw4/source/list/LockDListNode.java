package list;
 
class LockDListNode extends DListNode {
	int Locked;
	LockDListNode(Object item, DListNode p, DListNode n) {
		super(item, p, n);
		Locked = 0;
	}
}

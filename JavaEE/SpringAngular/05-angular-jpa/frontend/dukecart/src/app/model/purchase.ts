import { Address } from "./address";
import { Customer } from "./customer";
import { OrderItem } from "./order-item";
import { PurchaseOrder } from "./purchase-order";

export interface Purchase
{
  customer: Customer;
  shippingAddress: Address;
  billingAddress: Address;
  purchaseOrder: PurchaseOrder;
  orderItems: OrderItem[];
}
